package com.agomez.ahorcado.controller;

import com.agomez.ahorcado.model.UsuarioLoginDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("acceso")
public class AccesoController {

    private int contador;
    private Cookie contadorVisitas = null;

    @GetMapping("login")
    public ModelAndView devuelveFormularioLogin(HttpServletRequest sol, HttpServletResponse res){

        ModelAndView mAV = new ModelAndView();

        UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        Map<String, String> listaErrores = new HashMap<String, String>();

        if (buscacookies("_contador", sol.getCookies()) == null || contadorVisitas == null) {

            contador = 1;

            contadorVisitas = new Cookie("_contador", String.valueOf(contador));
            contadorVisitas.setPath("/");
            res.addCookie(contadorVisitas);

            mAV.addObject("bienvenida","Bienvenido por primera vez");

        } else {

            try {
                contador = Integer.parseInt(buscacookies("_contador", sol.getCookies()).getValue());
                System.out.println(contador);
            } catch (Exception e){
                System.out.println(contador);
                e.printStackTrace();
            }

            contador++;
            contadorVisitas = buscacookies("_contador", sol.getCookies());
            contadorVisitas.setValue(contador+"");
            contadorVisitas.setPath("/");
            res.addCookie(contadorVisitas);

            mAV.addObject("bienvenida","Bienvenido de nuevo");

        }

        mAV.addObject("ip",sol.getLocalAddr());
        mAV.addObject("User",sol.getHeader("User-Agent"));
        mAV.addObject("nombreUsuario","");
        mAV.addObject("usuarioLogin",usuarioLogin);
        mAV.addObject("listaErrores",listaErrores);
        mAV.addObject("contador",contadorVisitas.getValue());

        mAV.setViewName("login");

        return mAV;
    }

    @PostMapping("login")
    public ModelAndView recibeCredencialesLogin(UsuarioLoginDTO usuarioLogin, HttpServletRequest sol,HttpServletResponse res) throws UnknownHostException {

        ModelAndView mAV = new ModelAndView();

        Map<String, String> listaErrores = new HashMap<String, String>();

        List<UsuarioLoginDTO> listaUsuarios = recuperaUsuario();

        for (int i = 0; i < listaUsuarios.size(); i++) {

            if (usuarioLogin.getUsuario().equals(listaUsuarios.get(i).getUsuario())) {

                if (usuarioLogin.getClave().equals(listaUsuarios.get(i).getClave())) {

//                    sol.getSession().invalidate();
                    sol.getSession().setAttribute("usuarioLogin",usuarioLogin);

                    mAV.setViewName("redirect:/juego/ahorcado");
                    return mAV;
                } else {

                    listaErrores.put("clave", "Contraseña incorrecta");

                    mAV.setViewName("login");
                    mAV.addObject("usuarioLogin",usuarioLogin);
                    mAV.addObject("listaErrores",listaErrores);

                }
            } else {

                listaErrores.put("usuario", "Usuario incorrecto");

                mAV.setViewName("login");
                mAV.addObject("usuarioLogin",usuarioLogin);
                mAV.addObject("listaErrores",listaErrores);

            }

        }

        return mAV;
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest sol){

        ModelAndView mAV = new ModelAndView();

        sol.getSession().invalidate();

        mAV.setViewName("redirect:login");

        return mAV;

    }

    public List<UsuarioLoginDTO> recuperaUsuario() {

        File registros = new File("./src/main/resources/registros.json");
        List<UsuarioLoginDTO> usuarios = new ArrayList<>();
        try {

            ObjectMapper mapper = new ObjectMapper();

            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            try {

                for (int i = 0; i <registros.length(); i++) {

                    UsuarioLoginDTO u = mapper.readValue(registros, UsuarioLoginDTO.class);
                    usuarios.add(u);

                }

            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
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
