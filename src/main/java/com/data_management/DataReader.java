package com.data_management;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;


public interface DataReader {
    /**
     * Reads data from a specified source and stores it in the data storage.
     *
     * @param dataStorage the storage where data will be stored
     * @throws IOException if there is an error reading the data
     */
    void readData(DataStorage dataStorage) throws IOException;


        /**
         * Connects to a WebSocket server and continuously reads data.
         *
         * @param dataStorage the storage where data will be stored
         * @param port of the WebSocket server
         * @throws IOException if there is an error reading the data
         */
        void readData_websocket(DataStorage dataStorage, int port ) throws IOException;

}


