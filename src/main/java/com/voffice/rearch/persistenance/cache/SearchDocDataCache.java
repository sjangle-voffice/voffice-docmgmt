package com.voffice.rearch.persistenance.cache;

import com.voffice.rearch.persistenance.entity.dto.ActiveEmpDTO;
import com.voffice.rearch.persistenance.entity.dto.DocumentSrcDTO;
import com.voffice.rearch.persistenance.entity.dto.DocumentTypeDTO;
import com.voffice.rearch.persistenance.entity.dto.ProjectMasterDTO;
import com.voffice.rearch.persistenance.repository.DocumentSrcTblRepository;
import com.voffice.rearch.persistenance.repository.DocumentTypeTblRepository;
import com.voffice.rearch.persistenance.repository.EmployeeTblRepository;
import com.voffice.rearch.persistenance.repository.ProjectMasterTblRepository;
import com.voffice.rearch.utils.misc.ApplPageEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SearchDocDataCache {

    private Map<Integer, SearchDocCache> searchDataCacheMap = new ConcurrentHashMap<>();

    @Autowired
    private EmployeeTblRepository employeeTblRepository;

    @Autowired
    private DocumentTypeTblRepository documentTypeTblRepository;

    @Autowired
    private DocumentSrcTblRepository documentSrcTblRepository;

    @Autowired
    private ProjectMasterTblRepository projectMasterTblRepository;

    @Getter
    @Setter
    public class SearchDocCache {
        private long cacheRefreshTime;
        private int pageId;
        private List<ActiveEmpDTO> createrList;
        private List<DocumentTypeDTO> documentTypesList;
        private List<DocumentSrcDTO> documentSourcesList;
        private List<ProjectMasterDTO> projectNamesList;
    }

    public synchronized void initializeSearchDocCache(int pageId) {
            long totalMemory = Runtime.getRuntime().totalMemory();
            long availMemory = (totalMemory - Runtime.getRuntime().freeMemory());
            System.out.println("Initializing cache for Feed Id: "
                    + " System Total Memory: " + (totalMemory / (1024*1024)) + " MB, "
                    + " System Available Memory before Drools cache creation: "
                    + (availMemory / (1024*1024)) + " MB");

            SearchDocCache searchDocCache = new SearchDocCache();
            searchDocCache.setPageId(ApplPageEnum.SRCH_DOC.ordinal());

            // Code to fetch Creator List
            searchDocCache.setCreaterList(employeeTblRepository.findByActiveStatusEmployees());

            // Code to Document Type List
            searchDocCache.setDocumentTypesList(documentTypeTblRepository.fetchAllDocumentTypes());

            // Code to Document Source List
            searchDocCache.setDocumentSourcesList(documentSrcTblRepository.fetchAllDocumentSources());

            // Code to Project Names List
            searchDocCache.setProjectNamesList(projectMasterTblRepository.fetchAllActiveProjects());

            searchDataCacheMap.put(ApplPageEnum.SRCH_DOC.ordinal(), searchDocCache);

            availMemory = totalMemory - Runtime.getRuntime().freeMemory();
            System.out.println("Initialized cache for Feed Id: "
                + " System Total Memory: " + (totalMemory / (1024*1024)) + " MB, "
                + " System Available Memory after Drools cache creation: " + (availMemory / (1024*1024)) + " MB");
    }

    public Optional<SearchDocCache> getSearchDataCache(int pageId) {
        SearchDocCache searchDocCache = searchDataCacheMap.get(pageId);
        if (searchDocCache == null) {
            throw new IllegalStateException("****** Feed Data Cache is empty for: " + pageId + " ******");
        } else {
            return Optional.of(searchDocCache);
        }
    }

    /**
     * Refresh the cache if needed / any backend data changed.
     * @param pageId
     */
    public synchronized void refreshCache(int pageId) {
        SearchDocCache cache = searchDataCacheMap.get(pageId);
        if (cache != null) {
            if (isRefreshNeeded(pageId, cache.cacheRefreshTime)) {
                searchDataCacheMap.remove(pageId);
                SearchDocCache searchDocCache = new SearchDocCache();
                searchDocCache.pageId = pageId;
                searchDocCache.cacheRefreshTime = System.currentTimeMillis();

                // Code to fetch Creator List
                searchDocCache.setCreaterList(employeeTblRepository.findByActiveStatusEmployees());

                // Code to Document Type List
                searchDocCache.setDocumentTypesList(documentTypeTblRepository.fetchAllDocumentTypes());

                // Code to Document Source List
                searchDocCache.setDocumentSourcesList(documentSrcTblRepository.fetchAllDocumentSources());

                // Code to Project Names List
                searchDocCache.setProjectNamesList(projectMasterTblRepository.fetchAllActiveProjects());

                searchDataCacheMap.put(pageId, searchDocCache);
            }
        }
    }

    /**
     * Do we need to refresh the cache for this given Cust Id
     * @param pageId
     * @param prevCacheTime
     * @return
     */
    private boolean isRefreshNeeded(int pageId, long prevCacheTime) {
        return false;
    }

    /**
     * Remove the cache for given Feed
     *
     * @param feedId
     */
    public void removeCacheForFeed(String feedId) {
        searchDataCacheMap.remove(feedId);
    }
}