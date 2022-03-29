package com.agomez.ahorcado.controller;

import com.agomez.ahorcado.model.Palabra;
import com.agomez.ahorcado.model.UsuarioLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/juego")
@Controller
public class JuegoController {

    private int contador;
    private Cookie contadorVisitas;


    @GetMapping("/ahorcado")
    public ModelAndView juegoAhorcado(HttpSession sesion, HttpServletResponse res, HttpServletRequest sol) {

        ModelAndView mAV = new ModelAndView();
        UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();

        if (sesion.getAttribute("usuarioLogin") == null) {

            mAV.setViewName("redirect:/acceso/login");
            sesion.invalidate();
        } else { // Si

            usuarioLogin = (UsuarioLoginDTO) sesion.getAttribute("usuarioLogin");

            contadorVisitas = WebUtils.getCookie(sol, "_contador");

            Palabra palabra;

            contador = Integer.parseInt(contadorVisitas.getValue());

            contador++;
            contadorVisitas = new Cookie("_contador", String.valueOf(contador));

            contadorVisitas.setPath("/");
            res.addCookie(contadorVisitas);

            mAV.addObject("bienvenida", "Bienvenido de nuevo");

            if (sesion.getAttribute("palabra") == null) {

                palabra = new Palabra();

                sesion.setAttribute("palabra", palabra);
            } else {

                palabra = (Palabra) sesion.getAttribute("palabra");
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
                sesion.setAttribute("palabra", new Palabra());
            } else {

                resultado = "Partida en curso";
            }

            mAV.addObject("nombreUsuario", usuarioLogin.getUsuario());
            mAV.addObject("ip", sol.getLocalAddr());
            mAV.addObject("User", sol.getHeader("User-Agent"));
            mAV.addObject("numIntentos", palabra.getNumIntentos());
            mAV.addObject("intentos", palabra.getIntentos());
            mAV.addObject("palabra", palabra.getPalabraT());
            mAV.addObject("resultado", resultado);
            mAV.addObject("contador", contadorVisitas.getValue());
            mAV.setViewName("ahorcado");
        }
        return mAV;
    }


    @PostMapping("ahorcado")
    public ModelAndView compruebaJuego(HttpSession sesion, String letra) {

        ModelAndView mAV = new ModelAndView();
        Palabra palabra;
        palabra = (Palabra) sesion.getAttribute("palabra");

        palabra.compruebaLetra(letra);

        sesion.setAttribute("palabra",palabra);

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
