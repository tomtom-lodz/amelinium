package com.tomtom.amelinium.projectservice.model;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.tomtom.amelinium.projectservice.snapshot.BacklogSnapshot;
import com.tomtom.amelinium.projectservice.snapshot.ChartSnapshot;
import com.tomtom.amelinium.projectservice.snapshot.ProjectHistoryItem;

public class OfyService {
	
	static {
        ObjectifyService.factory().register(Project.class);
        ObjectifyService.factory().register(ProjectHistoryItem.class);
//        ObjectifyService.factory().register(BacklogSnapshot.class);
//        ObjectifyService.factory().register(ChartSnapshot.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }	

}
