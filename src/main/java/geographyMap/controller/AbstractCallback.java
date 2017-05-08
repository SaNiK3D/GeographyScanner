package geographyMap.controller;

import eventbus.Callback;

/**
 * Created by User on 08.05.2017
 */
abstract class AbstractCallback implements Callback {

    abstract void onSuccess();
}