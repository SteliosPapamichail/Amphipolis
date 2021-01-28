package com.csd4020.exceptions;

public class Exceptions {
    public static class IllegalTileTypeException extends Exception {
        public IllegalTileTypeException(String msg) {
            super(msg);
        }
    }

    public static class NoSuchTileFoundException extends Exception {
        public NoSuchTileFoundException(String msg) {
            super(msg);
        }
    }

    public static class NotRemovableTileException extends Exception {
        public NotRemovableTileException(String msg) {
            super(msg);
        }
    }
}
