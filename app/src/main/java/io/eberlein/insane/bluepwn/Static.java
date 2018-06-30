package io.eberlein.insane.bluepwn;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class Static {
    static final String TABLE_STAGE = "stage";
    static final String TABLE_STAGER = "stager";
    static final String TABLE_SERVICE = "service";
    static final String TABLE_SCAN = "scan";
    static final String TABLE_OUI = "oui";
    static final String TABLE_LOCATION = "location";
    static final String TABLE_DEVICE = "device";
    static final String TABLE_DESCRIPTOR = "descriptor";
    static final String TABLE_CHARACTERISTIC = "characteristic";
    static final String TABLE_SETTINGS = "settings";

    static final String[] TABLES = {
            TABLE_STAGE, TABLE_STAGER, TABLE_SERVICE, TABLE_SCAN, TABLE_OUI, TABLE_LOCATION,
            TABLE_DEVICE, TABLE_DESCRIPTOR, TABLE_CHARACTERISTIC
    };

    static final String KEY_REMOTE_DATABASE_SETTINGS = "remote";

    static final Integer EVENT_DISCOVERY_STARTED = 0;
    static final Integer EVENT_DISCOVERY_FINISHED = 1;
    static final Integer EVENT_DEVICE_DISCOVERED = 2;
    static final Integer EVENT_SDP_SCAN_FINISHED = 3;
    static final Integer EVENT_GATT_SCAN_FINISHED = 4;
    static final Integer EVENT_TO_SCAN_DEVICES_EMPTY = 5;
    static final Integer EVENT_START_SCANNING = 6;
    static final Integer EVENT_STOP_SCANNING = 7;
    static final Integer EVENT_GOT_COOKIE = 8;

    static final int BLUETOOTH_RESULT = 0;
    static final int LOCATION_RESULT = 1;

    static final String TYPE_DUAL = "dual";
    static final String TYPE_LE = "le";
    static final String TYPE_CLASSIC = "classic";
    static final String TYPE_UNKNOWN = "unknown";

    static final String BOND_BONDED = "bonded";
    static final String BOND_BONDING = "bonding";
    static final String BOND_UNKNOWN = "unknown";
    static final String BOND_NONE = "none";

    static final String URL_TABLE_VARIABLE = "{{TBL}}";
    static final String URL_AUTHENTICATE = "api/authenticate";
    static final String URL_TABLE_DIFFERENCE = "api/{{TBL}}/difference";
    static final String URL_TABLE_UPDATE = "api/{{TBL}}/update";
    static final String URL_TABLE_KEYS = "api/{{TBL}}/keys";
    static final String URL_TABLE_GET = "api/{{TBL}}/get";

    static List<String> jsonArrayToStringList(JsonArray l){
        List<String> r = new ArrayList<>();
        for(JsonElement e : l) r.add(e.getAsString());
        return r;
    }
}
