package com.example.littlewolf_pc.app;

public class SingletonUsuario {

    private static final SingletonUsuario INSTANCE = new SingletonUsuario();

    private SingletonUsuario(){}

    public static SingletonUsuario getInstance(){
        return INSTANCE;
    }
}
