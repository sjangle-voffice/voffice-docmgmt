package com.voffice.rearch.persistenance.cache;

import com.voffice.rearch.persistenance.entity.dto.ActiveTagsDTO;
import com.voffice.rearch.persistenance.entity.dto.CommunityDTO;
import com.voffice.rearch.persistenance.entity.dto.EmpCommunityDTO;
import com.voffice.rearch.persistenance.entity.dto.ProjectMasterDTO;
import com.voffice.rearch.persistenance.repository.CommunityMasterTblRepository;
import com.voffice.rearch.persistenance.repository.EmpCommunityMapTblRepository;
import com.voffice.rearch.persistenance.repository.MasterTagsTblRepository;
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
public class UploadDocDataCache {

    private Map<Integer, UploadDocCache> uploadDocCacheMap = new ConcurrentHashMap<>();

    @Autowired
    private MasterTagsTblRepository masterTagsTblRepository;

    @Autowired
    private ProjectMasterTblRepository projectMasterTblRepository;

    @Autowired
    private CommunityMasterTblRepository communityMasterTblRepository;

    @Autowired
    private EmpCommunityMapTblRepository empCommunityMapTblRepository;

    public UploadDocDataCache() {
    }

    @Getter
    @Setter
    public class UploadDocCache {
        private long cacheRefreshTime;
        private int pageId;
        private List<ActiveTagsDTO> activeTagsList;
        private List<ProjectMasterDTO> projectNamesList;
        private List<CommunityDTO> communityMasterMappingList;
        private List<EmpCommunityDTO> empCommunityMappingList;
    }

    public synchronized void initializeUploadDocCache(int pageId) {
            long totalMemory = Runtime.getRuntime().totalMemory();
            long availMemory = (totalMemory - Runtime.getRuntime().freeMemory());
            System.out.println("Initializing cache for Feed Id: "
                    + " System Total Memory: " + (totalMemory / (1024*1024)) + " MB, "
                    + " System Available Memory before Drools cache creation: "
                    + (availMemory / (1024*1024)) + " MB");

        UploadDocCache uploadDocCache = new UploadDocCache();
        uploadDocCache.setPageId(pageId);

        // Code to fetch Active Tags List
        uploadDocCache.setActiveTagsList (masterTagsTblRepository.fetchAllMasterTags());

        // Code to Project Names List
        uploadDocCache.setProjectNamesList(projectMasterTblRepository.fetchAllActiveProjects());

        // Code to Community Master Mapping List
        uploadDocCache.setCommunityMasterMappingList(communityMasterTblRepository.fetchAllCommunities());

        // Code to Project Names List
        uploadDocCache.setEmpCommunityMappingList(empCommunityMapTblRepository.fetchAllEmpCommunitiesMappings());

        uploadDocCacheMap.put(ApplPageEnum.UPLD_DOC.ordinal(), uploadDocCache);

        availMemory = totalMemory - Runtime.getRuntime().freeMemory();
        System.out.println("Initialized cache for Feed Id: "
            + " System Total Memory: " + (totalMemory / (1024*1024)) + " MB, "
            + " System Available Memory after Drools cache creation: " + (availMemory / (1024*1024)) + " MB");
    }

    public Optional<UploadDocCache> getSearchDataCache(int pageId) {
        UploadDocCache uploadDocCache = uploadDocCacheMap.get(pageId);
        if (uploadDocCache == null) {
            throw new IllegalStateException("****** Feed Data Cache is empty for: " + pageId + " ******");
        } else {
            return Optional.of(uploadDocCache);
        }
    }

    /**
     * Refresh the cache if needed / any backend data changed.
     * @param pageId
     */
    public synchronized void refreshCache(int pageId) {
        UploadDocCache cache = uploadDocCacheMap.get(pageId);
        if (cache != null) {
            if (isRefreshNeeded(pageId, cache.cacheRefreshTime)) {
                uploadDocCacheMap.remove(pageId);
                UploadDocCache uploadDocCache = new UploadDocCache();
                uploadDocCache.pageId = pageId;
                uploadDocCache.cacheRefreshTime = System.currentTimeMillis();

                // Code to fetch Active Tags List
                uploadDocCache.setActiveTagsList (masterTagsTblRepository.fetchAllMasterTags());

                // Code to Project Names List
                uploadDocCache.setProjectNamesList(projectMasterTblRepository.fetchAllActiveProjects());

                // Code to Community Master Mapping List
                uploadDocCache.setCommunityMasterMappingList(communityMasterTblRepository.fetchAllCommunities());

                // Code to Project Names List
                uploadDocCache.setEmpCommunityMappingList(empCommunityMapTblRepository.fetchAllEmpCommunitiesMappings());

                uploadDocCacheMap.put(pageId, uploadDocCache);
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
        uploadDocCacheMap.remove(feedId);
    }
}