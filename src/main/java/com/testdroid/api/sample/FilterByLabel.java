package com.testdroid.api.sample;

import com.testdroid.api.APIClient;
import com.testdroid.api.APIDeviceQueryBuilder;
import com.testdroid.api.APIException;
import com.testdroid.api.APIListResource;
import com.testdroid.api.model.APIDevice;
import com.testdroid.api.sample.util.Common;

public class FilterByLabel {
    public static final APIClient CLIENT = Common.createApiClient();

    public static void main(String[] args) {
        try {
            // Get all devices
            APIListResource<APIDevice> devicesResource = CLIENT.getDevices();
            System.out.println(String.format("Get %s devices", devicesResource.getTotal()));
            //printDeviceNames(devicesResource);
            
            // Search device
            String deviceName = devicesResource.getEntity().get(0).getDisplayName();
            devicesResource = CLIENT.getDevices(new APIDeviceQueryBuilder().offset(0).limit(10).search(deviceName));
            System.out.println(String.format("Found %s devices", devicesResource.getTotal()));
            //printDeviceNames(devicesResource);      
            
            // Search device with filter
            devicesResource = CLIENT.getDevices(new APIDeviceQueryBuilder().filterWithLabelIds());
            System.out.println(String.format("Found %s recomended devices", devicesResource.getTotal()));
            printDeviceNames(devicesResource);
            

            
            
           
            
            
            
            
            

        } catch (APIException apie) {
            System.err.println(apie.getMessage());
        }
    }

    private static void printDeviceNames(APIListResource<APIDevice> devicesResource) throws APIException {
        for (APIDevice device : devicesResource.getEntity().getData()) {
            System.out.println(device.getDisplayName());
        }
    }
}
