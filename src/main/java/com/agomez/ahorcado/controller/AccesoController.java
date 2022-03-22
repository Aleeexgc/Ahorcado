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
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/acceso")
public class AccesoController {

    private int contador;
    private Cookie contadorVisitas = null;

    @GetMapping("login")
    public ModelAndView devuelveFormularioLogin(HttpServletRequest sol, HttpServletResponse res) throws UnknownHostException {

        ModelAndView mAV = new ModelAndView();

        UsuarioLoginDTO usuarioLogin = new UsuarioLoginDTO();
        Map<String, String> listaErrores = new HashMap<String, String>();

        if (sol.getCookies() == null) {

            contador = 1;

            contadorVisitas = new Cookie("_contador", String.valueOf(contador));

            res.addCookie(contadorVisitas);

            mAV.addObject("bienvenida","Bienvenido por primera vez");

        } else {


            contador = Integer.parseInt(sol.getCookies()[0].getValue());

            contador++;
            contadorVisitas = new Cookie("_contador", String.valueOf(contador));

            res.addCookie(contadorVisitas);

            mAV.addObject("bienvenida","Bienvenido de nuevo");

        }
        mAV.addObject("ip",sol.getLocalAddr());
        mAV.addObject("User",sol.getHeader("User-Agent"));
        mAV.addObject("nombreUsuario",usuarioLogin.getUsuario());
        mAV.addObject("usuarioLogin",usuarioLogin);
        mAV.addObject("listaErrores",listaErrores);
        mAV.addObject("contador",contadorVisitas.getValue());

        mAV.setViewName("login");

        return mAV;
    }

    @PostMapping("login")
    public ModelAndView recibeCredencialesLogin(UsuarioLoginDTO usuarioLogin, HttpServletRequest sol) throws UnknownHostException {

        ModelAndView mAV = new ModelAndView();

        Map<String, String> listaErrores = new HashMap<String, String>();

        List<UsuarioLoginDTO> lista = recuperaUsuario();

        for (int i = 0; i < lista.size(); i++) {

            if (usuarioLogin.getUsuario().equals(lista.get(i).getUsuario())) {

                if (usuarioLogin.getClave().equals(lista.get(i).getClave())) {

                    mAV.setViewName("redirect:/juego/ahorcado");

                } else {

                    listaErrores.put("clave", "Contraseña incorrecta");

                    mAV.setViewName("login");

                }
            } else {

                listaErrores.put("usuario", "Usuario incorrecto");

                mAV.setViewName("login");

            }


        }

        System.out.println(usuarioLogin.getUsuario());
        sol.getSession().setAttribute("contadorVisitas",contadorVisitas);
        sol.getSession().setAttribute("usuarioLogin",usuarioLogin);

        mAV.addObject("usuarioLogin",usuarioLogin);
        mAV.addObject("listaErrores",listaErrores);

        //		mAV.setViewName("");

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

}
