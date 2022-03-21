package com.agomez.ahorcado.controller;

import com.agomez.ahorcado.model.Palabra;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RequestMapping("juego")
@Controller
public class JuegoController {

    @GetMapping("ahorcado")
    public ModelAndView juegoAhorcado(HttpServletRequest sol) throws UnknownHostException {

        ModelAndView mAV = new ModelAndView();
        Palabra palabra;

        if (sol.getSession().getAttribute("palabra") == null){

            palabra = new Palabra();

            sol.getSession().setAttribute("palabra",palabra);
        } else {

            palabra = (Palabra) sol.getSession().getAttribute("palabra");
        }

        String resultado = "";

        if (palabra.compruebaGanador() || palabra.getNumIntentos() == 0){

            if (palabra.compruebaGanador()){

                resultado = "Usted ha ganado";

            } else {

                if (palabra.getNumIntentos() == 0){

                    resultado = "Usted ha perdido";

                }
            }
        } else{

            resultado="Partida en curso";
        }
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        String name = addr.getHostName();
        mAV.addObject("ip",ip);
        mAV.addObject("User",sol.getHeader("User-Agent"));

        mAV.addObject("numIntentos",palabra.getNumIntentos());
        mAV.addObject("intentos",palabra.getIntentos());
        mAV.addObject("palabra",palabra.getPalabraT());
        mAV.addObject("resultado",resultado);
        mAV.setViewName("ahorcado");

        return mAV;
    }


    @PostMapping("ahorcado")
    public ModelAndView compruebaJuego(HttpServletRequest sol, String letra) {

        ModelAndView mAV = new ModelAndView();
        Palabra palabra = null;
        palabra = (Palabra) sol.getSession().getAttribute("palabra");

        palabra.compruebaLetra(letra);

        sol.getSession().setAttribute("palabra",palabra);

//        mAV.addObject("palabra",palabra.getPalabraT());
        mAV.setViewName("redirect:ahorcado");

        return mAV;
    }
}
