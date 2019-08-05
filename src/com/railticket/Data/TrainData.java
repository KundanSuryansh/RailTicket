package com.railticket.Data;

import com.railticket.TransportMode.Train;
import com.railticket.repository.TrainDao;

public class TrainData {
    public TrainData()
    {
        String[] rajdhaniStations=new String[]{"delhi","lucknow","kanpur","mughalsarai","patna"};
        Train rajdhani=new Train(1,"Rajdhani",rajdhaniStations);
        TrainDao.getInstance().save(rajdhani);

        String[] tweleveDownStations=new String[]{"danapur","Ara","buxar","dildarnagar","kuchman","varanasi"};
        Train twelveDown=new Train(2,"12Down",tweleveDownStations);
        TrainDao.getInstance().save(twelveDown);

        String[] shatabdiStations=new String[]{"delhi","gurgaon","lucknow","kanpur","mughalsarai","aasansol","durgapur"};
        Train shatabdi=new Train(3,"shatabdi",shatabdiStations);
        TrainDao.getInstance().save(shatabdi);

        String[] shramjeeviStations=new String[]{"rajgir","patna","danapur","ara","buxar","mughalsarai","varanasi","illahabad","lucknow","gurgaon","delhi"};
        Train shramjeevi=new Train(4,"shramjeevi",shramjeeviStations);
        TrainDao.getInstance().save(shramjeevi);
    }
}
