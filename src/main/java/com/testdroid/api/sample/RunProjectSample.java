package com.testdroid.api.sample;

import com.testdroid.api.APIClient;
import com.testdroid.api.APIException;
import com.testdroid.api.APIList;
import com.testdroid.api.model.APIDeviceGroup;
import com.testdroid.api.model.APIDeviceRun;
import com.testdroid.api.model.APIFiles;
import com.testdroid.api.model.APIProject;
import com.testdroid.api.model.APITestRun;
import com.testdroid.api.model.APITestRunConfig;
import com.testdroid.api.model.APIUser;
import com.testdroid.api.model.AndroidFiles;
import com.testdroid.api.sample.util.Common;
import java.io.File;

/**
 *
 * @author Sławomir Pawluk <slawomir.pawluk@bitbar.com>
 */
public class RunProjectSample {
    
    public static final APIClient CLIENT = Common.createApiClient();
    public static final String HOOK_URL = "some URL";
    
    public static void main(String[] args) {
        try {
            // Get authenticated user
            APIUser me = CLIENT.me();
            
            // Create project
            APIProject project = me.createProject(APIProject.Type.ANDROID);
            
            // Get or create device group
            APIList<APIDeviceGroup> deviceGroupsList = project.getPublicDeviceGroups().getEntity();
            APIDeviceGroup deviceGroup = deviceGroupsList.get(0);
            
            // Get test run config from project
            APITestRunConfig testRunConfig = project.getTestRunConfig();
            
            // Configure test run config
            testRunConfig.setApplicationPassword("applicationPassword");
            testRunConfig.setApplicationUsername("applicationUsername");
            testRunConfig.setAutoScreenshots(false);
            testRunConfig.setCheckApp(true);
            testRunConfig.setDeviceLanguageCode("EN");
            testRunConfig.setUsedDeviceGroupId(deviceGroup.getId());
            testRunConfig.setMode(APITestRunConfig.Mode.FULL_RUN);
            
            // Set hook URL to recieve signal, when test run is finished.
            // Server will send POST request to specified URL:
            // POST body:
            // projectId=[0-9]* - id of finished project
            // testRunId=[0-9]* - id of finished test run
            // status=FINISHED
            testRunConfig.setHookURL(HOOK_URL);
            
            // Update changes
            testRunConfig.update();
            
            // Upload application
            project.uploadApplication(new File(RunProjectSample.class.getResource(Common.ANDROID_APPLICATION_RESOURCE_PATH).getPath()), Common.ANDROID_FILE_MIME_TYPE);
            
            // Upload test
            project.uploadTest(new File(RunProjectSample.class.getResource(Common.ANDROID_TEST_RESOURCE_PATH).getPath()), Common.ANDROID_FILE_MIME_TYPE);
            
            // Upload data
            project.uploadData(new File(RunProjectSample.class.getResource(Common.DATA_FILE_RESOURCE_PATH).getPath()), Common.ZIP_FILE_MIME_TYPE);
            
            // Run test run
            APITestRun testRun = project.run("My test run name");
            
            System.out.println(String.format("Creted testrun with name: %s", testRun.getDisplayName()));
            System.out.println("Device runs was also created for devices:");
            
            for(APIDeviceRun deviceRun: testRun.getDeviceRunsResource().getEntity().getData()) {
                System.out.println(String.format("Device: %s, created: %s", 
                        deviceRun.getDevice().getDisplayName(), deviceRun.getCreateTime()));
            }
            
            // After sending files to Testdroid Cloud files can be send back
            AndroidFiles androidFiles = project.getFiles(AndroidFiles.class);
            
            APIFiles.AndroidAppFile androidAppFile = androidFiles.getAndroidApp();
            APIFiles.AndroidTestFile androidTestFile = androidFiles.getAndroidTest();
            
        } catch(APIException apie) {
            System.err.println(apie.getMessage());
        }
    }
            
}
