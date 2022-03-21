package com.agomez.ahorcado.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class Palabra {

   private  String[] palabras = {"elefante", "bombilla", "perro", "television"};

   private  String palabra = "";

   private  int longitudPalabra;

   private  char[] palabraT;

   private int numIntentos = 6;

    private HashMap<Integer,String> intentos = new HashMap<Integer, String>();

    public Palabra(){
        
        palabra = palabras[(int)(Math.random()*3)];

        longitudPalabra = palabra.length();

        palabraT = new char[longitudPalabra];

        for (int i = 0; i < palabraT.length; i++) {

            palabraT[i] =  '_';

        }

        intentos.put(0,"cero");
        intentos.put(1,"uno");
        intentos.put(2,"dos");
        intentos.put(3,"tres");
        intentos.put(4,"cuatro");
        intentos.put(5,"cinco");
        intentos.put(6,"seis");
    }

    public void compruebaLetra(String letra) {

        if(palabra.contains(letra)) {

            for (int i = 0; i < palabra.length(); i++) {

                if (palabra.charAt(i) == letra.charAt(0)) {

                    palabraT[i] = palabra.charAt(i);
                }

            }
        } else{

            numIntentos--;
        }
    }

    public boolean compruebaGanador(){

        if (String.valueOf(palabraT).equals(palabra)){

            return true;
        } else {

            return false;
        }
    }

    public String[] getPalabras() {
        return palabras;
    }

    public void setPalabras(String[] palabras) {
        this.palabras = palabras;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getLongitudPalabra() {
        return longitudPalabra;
    }

    public void setLongitudPalabra(int longitudPalabra) {
        this.longitudPalabra = longitudPalabra;
    }

    public char[] getPalabraT() {
        return palabraT;
    }

    public void setPalabraT(char[] palabraT) {
        this.palabraT = palabraT;
    }

    public int getNumIntentos() {
        return numIntentos;
    }

    public void setNumIntentos(int numIntentos) {
        this.numIntentos = numIntentos;
    }

    public HashMap<Integer, String> getIntentos() {
        return intentos;
    }

    public void setIntentos(HashMap<Integer, String> intentos) {
        this.intentos = intentos;
    }
}
