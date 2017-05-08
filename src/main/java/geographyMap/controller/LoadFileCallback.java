package geographyMap.controller;

import eventbus.Callback;

/**
 * Created by User on 08.05.2017
 */
abstract class LoadFileCallback implements Callback {

    abstract void onSuccess();
}