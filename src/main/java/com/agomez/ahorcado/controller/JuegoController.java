package com.agomez.ahorcado.controller;

import com.agomez.ahorcado.model.Palabra;
import com.agomez.ahorcado.model.UsuarioLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RequestMapping("juego")
@Controller
public class JuegoController {

    private int contador;
    private Cookie contadorVisitas;

    @GetMapping("ahorcado")
    public ModelAndView juegoAhorcado(HttpServletRequest sol, HttpServletResponse res) throws UnknownHostException {

        ModelAndView mAV = new ModelAndView();
        Palabra palabra;
        UsuarioLoginDTO usuarioLogin = (UsuarioLoginDTO) sol.getSession().getAttribute("usuarioLogin");

        if (sol.getSession().getAttribute("contadorVisitas") != null) {

            contadorVisitas = (Cookie) sol.getSession().getAttribute("contadorVisitas");

            res.addCookie(contadorVisitas);
            contador = Integer.parseInt(contadorVisitas.getValue());

            if (sol.getCookies() == null) {

                System.out.println("aqui");
                contador = 1;

                contadorVisitas = new Cookie("_contador", String.valueOf(contador));

                res.addCookie(contadorVisitas);

                mAV.addObject("bienvenida","Bienvenido por primera vez");

            } else {

                System.out.println("o aqui");

                contador = Integer.parseInt(contadorVisitas.getValue());
                contador++;
                contadorVisitas = new Cookie("_contador", String.valueOf(contador));

                sol.getSession().setAttribute("contadorVisitas",contadorVisitas);

                res.addCookie(contadorVisitas);

                mAV.addObject("bienvenida","Bienvenido de nuevo");

            }
            mAV.addObject("contador",contadorVisitas.getValue());
        }



        if (usuarioLogin == null){

            mAV.setViewName("redirect:/acceso/login");
            System.out.println("pepe");

        } else {
            System.out.println(usuarioLogin.getUsuario() + "holaaaa");
            mAV.addObject("nombreUsuario",usuarioLogin.getUsuario());
        }

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
            sol.getSession().invalidate();
        } else{

            resultado="Partida en curso";
        }



        mAV.addObject("ip",sol.getLocalAddr());
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

//        sol.getSession().setAttribute("cookie",contadorVisitas);
        sol.getSession().setAttribute("palabra",palabra);

//        mAV.addObject("palabra",palabra.getPalabraT());
        mAV.setViewName("redirect:ahorcado");

        return mAV;
    }

}
