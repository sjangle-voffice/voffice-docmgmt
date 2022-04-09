package com.voffice.rearch.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO to encapsulate job details.
 * @author Sagar Jangle.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDetails {

    private long jobId;
    private String jobName;
    private String triggerTime;

}
