package com.vk.timemachine.adapter;

import com.vk.timemachine.model.TimeZoneModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeZoneAdapter {


    List<TimeZoneModel> timeZoneList = new ArrayList();

    public TimeZoneAdapter() {

        timeZoneList.add(new TimeZoneModel("Pacific/Apia", "West Samoa Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Niue", "Niue Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Pago_Pago", "Samoa Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Adak", "Hawaii-Aleutian Standard Time"));
        timeZoneList.add(new TimeZoneModel("HST", "Hawaii Standard Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Fakaofo", "Tokelau Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Honolulu", "Hawaii Standard Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Rarotonga", "Cook Is. Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Tahiti", "Tahiti Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Marquesas", "Marquesas Time"));
        timeZoneList.add(new TimeZoneModel("America/Anchorage", "Alaska Standard Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Gambier", "Gambier Time"));
        timeZoneList.add(new TimeZoneModel("America/Los_Angeles", "Pacific Standard Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Pitcairn", "Pitcairn Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Dawson_Creek", "Mountain Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Belize", "Central Standard Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Easter", "Easter Is. Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Galapagos", "Galapagos Time"));
        timeZoneList.add(new TimeZoneModel("America/Bogota", "Colombia Time"));
        timeZoneList.add(new TimeZoneModel("America/Cayman", "Eastern Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Guayaquil", "Ecuador Time"));
        timeZoneList.add(new TimeZoneModel("America/Havana", "Central Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Indianapolis", "Eastern Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Lima", "Peru Time"));
        timeZoneList.add(new TimeZoneModel("America/Porto_Acre", "Acre Time"));
        timeZoneList.add(new TimeZoneModel("America/Rio_Branco", "GMT-05:00"));
        timeZoneList.add(new TimeZoneModel("America/Anguilla", "Atlantic Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Asuncion", "\tParaguay Time"));
        timeZoneList.add(new TimeZoneModel("America/Cuiaba", "\tAmazon Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Guyana", "Guyana Time"));
        timeZoneList.add(new TimeZoneModel("America/La_Paz", "\tBolivia Time"));
        timeZoneList.add(new TimeZoneModel("America/Santiago", "Chile Time"));
        timeZoneList.add(new TimeZoneModel("PRT", "Falkland Is. Time"));
        timeZoneList.add(new TimeZoneModel("America/St_Johns", "Newfoundland Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Buenos_Aires", "Argentine Time"));
        timeZoneList.add(new TimeZoneModel("America/Cayenne", "French Guiana Time"));
        timeZoneList.add(new TimeZoneModel("America/Fortaleza", "Brazil Time"));
        timeZoneList.add(new TimeZoneModel("America/Godthab", "Western Greenland Time"));
        timeZoneList.add(new TimeZoneModel("America/Miquelon", "Pierre & Miquelon Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Montevideo", "Uruguay Time"));
        timeZoneList.add(new TimeZoneModel("America/Paramaribo", "Suriname Time"));
        timeZoneList.add(new TimeZoneModel("America/Noronha", "Fernando de Noronha Time"));
        timeZoneList.add(new TimeZoneModel("Atlantic/South_Georgia", "South Georgia Standard Time"));
        timeZoneList.add(new TimeZoneModel("America/Scoresbysund", "Eastern Greenland Time"));
        timeZoneList.add(new TimeZoneModel("Atlantic/Azores", "Azores Time"));
        timeZoneList.add(new TimeZoneModel("Atlantic/Cape_Verde", "Cape Verde Time"));
        timeZoneList.add(new TimeZoneModel("Africa/Abidjan\t", "Greenwich Mean Time\n"));
        timeZoneList.add(new TimeZoneModel("Africa/Casablanca", "Western European Time\n"));
        timeZoneList.add(new TimeZoneModel("UTC", "\tCoordinated Universal Time"));
        timeZoneList.add(new TimeZoneModel("Africa/Algiers", "Central European Time\n"));
        timeZoneList.add(new TimeZoneModel("Africa/Bangui", "\tWestern African Time"));
        timeZoneList.add(new TimeZoneModel("Africa/Cairo", "\tEastern European Time"));
        timeZoneList.add(new TimeZoneModel("Africa/Harare", "Central African Time"));
        timeZoneList.add(new TimeZoneModel("Africa/Johannesburg", "South Africa Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Jerusalem", "Israel Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Aden", "\tArabia Standard Time"));
        timeZoneList.add(new TimeZoneModel("Europe/Moscow", "Moscow Standard Time\n"));
        timeZoneList.add(new TimeZoneModel("Asia/Tehran", "Iran Time\n"));
        timeZoneList.add(new TimeZoneModel("Asia/Aqtau", "Aqtau Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Baku", "\tAzerbaijan Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Dubai", "Gulf Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Tbilisi", "Georgia Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Yerevan", "Armenia Time\n"));
        timeZoneList.add(new TimeZoneModel("Europe/Samara", "Samara Time\n"));
        timeZoneList.add(new TimeZoneModel("Indian/Mahe", "Seychelles Time"));
        timeZoneList.add(new TimeZoneModel("Indian/Mauritius", "Mauritius Time"));
        timeZoneList.add(new TimeZoneModel("Indian/Reunion", "\tReunion Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Kabul", "Afghanistan Time\n"));
        timeZoneList.add(new TimeZoneModel("Asia/Aqtobe", "Aqtobe Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Ashgabat", "Turkmenistan Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Bishkek", "Kirgizstan Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Dushanbe", "Tajikistan Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Karachi", "Pakistan Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Tashkent", "Uzbekistan Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Yekaterinburg", "\tYekaterinburg Time"));
        timeZoneList.add(new TimeZoneModel("Indian/Chagos", "Indian Ocean Territory Time"));
        timeZoneList.add(new TimeZoneModel("Indian/Kerguelen", "French Southern & Antarctic Lands Time"));
        timeZoneList.add(new TimeZoneModel("Indian/Maldives", "Maldives Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Calcutta", "India Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Katmandu", "Nepal Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Almaty", "Alma-Ata Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Colombo", "Sri Lanka Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Dacca", "Bangladesh Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Novosibirsk", "\tNovosibirsk Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Thimbu", "Bhutan Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Rangoon", "\tMyanmar Time"));
        timeZoneList.add(new TimeZoneModel("Indian/Cocos", "Cocos Islands Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Phnom_Penh", "Indochina Time"));
        timeZoneList.add(new TimeZoneModel("Indian/Christmas", "\tChristmas Island Time"));
        timeZoneList.add(new TimeZoneModel("Antarctica/Casey", "Western Standard Time (Australia)"));
        timeZoneList.add(new TimeZoneModel("Asia/Brunei", "\tBrunei Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Hong_Kong", "\tHong Kong Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Irkutsk", "Irkutsk Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Kuala_Lumpur", "Malaysia Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Shanghai", "China Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Singapore", "Singapore Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Ujung_Pandang", "Borneo Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Ulaanbaatar", "Ulaanbaatar Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Jayapura", "Jayapura Time\n"));
        timeZoneList.add(new TimeZoneModel("Asia/Pyongyang", "Korea Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Tokyo", "\tJapan Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Yakutsk", "Yakutsk Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Palau", "Palau Time"));
        timeZoneList.add(new TimeZoneModel("Australia/Darwin", "Central Standard Time (Northern Territory)"));
        timeZoneList.add(new TimeZoneModel("Australia/Adelaide", "Central Standard Time (South Australia)"));
        timeZoneList.add(new TimeZoneModel("Australia/Sydney", "Eastern Standard Time (New South Wales)"));
        timeZoneList.add(new TimeZoneModel("Antarctica/DumontDUrville", "Dumont-d'Urville Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Vladivostok", "Vladivostok Time"));
        timeZoneList.add(new TimeZoneModel("Australia/Brisbane", "Eastern Standard Time (Queensland)"));
        timeZoneList.add(new TimeZoneModel("Australia/Hobart", "\tEastern Standard Time (Tasmania)"));
        timeZoneList.add(new TimeZoneModel("Pacific/Guam", "\tChamorro Standard Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Port_Moresby", "Papua New Guinea Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Truk", "Truk Time"));
        timeZoneList.add(new TimeZoneModel("Australia/Lord_Howe", "Load Howe Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Magadan", "Magadan Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Efate", "\tVanuatu Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Guadalcanal", "Solomon Is. Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Kosrae", "\tKosrae Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Noumea", "New Caledonia Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Ponape", "Ponape Time"));
        timeZoneList.add(new TimeZoneModel("SST", "\tSolomon Is. Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Norfolk", "Norfolk Time"));
        timeZoneList.add(new TimeZoneModel("Antarctica/McMurdo", "New Zealand Standard Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Anadyr", "Anadyr Time"));
        timeZoneList.add(new TimeZoneModel("Asia/Kamchatka", "Petropavlovsk-Kamchatski Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Auckland", "New Zealand Standard Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Fiji", "Fiji Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Funafuti", "Tuvalu Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Majuro", "Marshall Islands Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Nauru", "Nauru Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Tarawa", "\tGilbert Is. Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Wake", "Wake Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Wallis", "Wallis & Futuna Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Chatham", "Chatham Standard Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Enderbury", "\tPhoenix Is. Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Tongatapu", "Tonga Time"));
        timeZoneList.add(new TimeZoneModel("Pacific/Kiritimati", "Line Is. Time"));

    }

    public List<String> getTimeZones() {
        return timeZoneList.stream().map(TimeZoneModel::getTimeZone).collect(Collectors.toList());
    }

    public String getTimeZoneId(String timeZone) {
        for (TimeZoneModel timeZoneModel : timeZoneList) {
            if(timeZoneModel.getTimeZone().equalsIgnoreCase(timeZone)) {
                return timeZoneModel.getTimeZoneId();
            }
        }
        return "N/A";
    }

}
