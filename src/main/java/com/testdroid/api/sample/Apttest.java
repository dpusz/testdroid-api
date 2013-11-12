package com.testdroid.api.sample;

import com.testdroid.api.APIClient;
import com.testdroid.api.APIDeviceQueryBuilder;
import com.testdroid.api.APIException;
import com.testdroid.api.APIList;
import com.testdroid.api.APIListResource;
import com.testdroid.api.APIQueryBuilder;
import com.testdroid.api.model.APIDevice;
import com.testdroid.api.model.APIDeviceGroup;
import com.testdroid.api.model.APIUser;
import com.testdroid.api.sample.util.Common;


public class Apttest {
	 public static final APIClient CLIENT = Common.createApiClient("http://testdroid:9080", "pusz", "tomtom123");
}
