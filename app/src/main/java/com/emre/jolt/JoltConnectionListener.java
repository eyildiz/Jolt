package com.emre.jolt;

/**
 * Created by Emre Davarci on 14.07.2015.
 */
public abstract class JoltConnectionListener {

    /**
     * @param result Returns the result from url.
     */
    public void onSuccess(String result){}

    /**
     * In any case of connection problems, onFail() is called.
     */
    public void onFail(){}

    /**
     * At the beginning of the connection, onStart() is called.
     */
    public void onStart(){}

    /**
     * At the end of the connection, onFinish() is called. Note that onFinish() is called after onFail().
     */
    public void onFinish(){}

}
