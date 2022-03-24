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
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/juego")
@Controller
public class JuegoController {

    private int contador;


    @GetMapping("/ahorcado")
    public ModelAndView juegoAhorcado(HttpServletRequest sol, HttpServletResponse res) {

        Cookie contadorVisitas;
        ModelAndView mAV = new ModelAndView();
        UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();

        if (sol.getSession().getAttribute("usuarioLogin") == null) {
            mAV.setViewName("redirect:/acceso/login");
            return mAV;
        } else {
            usuarioLogin = (UsuarioLoginDTO) sol.getSession().getAttribute("usuarioLogin");

        }
        Palabra palabra;


//        if (sol.getSession().getAttribute("usuarioLogin") != null) {
//
//            usuarioLogin = (UsuarioLoginDTO) sol.getSession().getAttribute("usuarioLogin");
//        }

        if (buscacookies("_contador", sol.getCookies()) == null) {

            contador = 1;

            contadorVisitas = new Cookie("_contador", String.valueOf(contador));
            contadorVisitas.setPath("/");
            res.addCookie(contadorVisitas);

            mAV.addObject("bienvenida","Bienvenido por primera vez");

        } else {

            try {
                contador = Integer.parseInt(buscacookies("_contador", sol.getCookies()).getValue());
            } catch (Exception e){

                e.printStackTrace();
            }


            contador++;
            contadorVisitas = buscacookies("_contador", sol.getCookies());

            contadorVisitas.setValue(contador+"");
            contadorVisitas.setPath("/");
            res.addCookie(contadorVisitas);

            mAV.addObject("bienvenida","Bienvenido de nuevo");

        }

        if (sol.getSession().getAttribute("palabra") == null) {

            palabra = new Palabra();

            sol.getSession().setAttribute("palabra", palabra);
        } else {

            palabra = (Palabra) sol.getSession().getAttribute("palabra");
        }

        String resultado = "";

        if (palabra.compruebaGanador() || palabra.getNumIntentos() == 0) {

            if (palabra.compruebaGanador()) {

                resultado = "Usted ha ganado";

            } else {

                if (palabra.getNumIntentos() == 0) {

                    resultado = "Usted ha perdido";

                }
            }
            sol.getSession().setAttribute("palabra", new Palabra());
        } else {

            resultado = "Partida en curso";
        }

        mAV.addObject("nombreUsuario", usuarioLogin.getUsuario());
        mAV.addObject("ip", sol.getLocalAddr());
        mAV.addObject("User", sol.getHeader("User-Agent"));
//            mAV.addObject("usuarioLogin", usuarioLogin);
        mAV.addObject("numIntentos", palabra.getNumIntentos());
        mAV.addObject("intentos", palabra.getIntentos());
        mAV.addObject("palabra", palabra.getPalabraT());
        mAV.addObject("resultado", resultado);
        mAV.addObject("contador",contadorVisitas.getValue());
        mAV.setViewName("ahorcado");

        return mAV;
    }


    @PostMapping("ahorcado")
    public ModelAndView compruebaJuego(HttpServletRequest sol, String letra) {

        ModelAndView mAV = new ModelAndView();
        Palabra palabra;
        palabra = (Palabra) sol.getSession().getAttribute("palabra");

        palabra.compruebaLetra(letra);

//        sol.getSession().setAttribute("cookie",contadorVisitas);
        sol.getSession().setAttribute("palabra",palabra);

//        mAV.addObject("palabra",palabra.getPalabraT());
        mAV.setViewName("redirect:ahorcado");

        return mAV;
    }

    // Hecho por Alejandro Gomez
    private Cookie buscacookies(String nombre, Cookie[] cookies){

        if (cookies != null){

            for (int i = 0; i < cookies.length; i++) {

                if (cookies[i].getName().equals(nombre));
                return cookies[i];
            }
        }

        return null;
    }
}
