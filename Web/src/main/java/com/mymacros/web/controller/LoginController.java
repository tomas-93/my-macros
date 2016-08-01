package com.mymacros.web.controller;

import com.mymacros.dto.entity.LoginDto;
import com.mymacros.dto.entity.UserAndProfileFormDto;
import com.mymacros.services.dao.entity.UserAndProfileServiceDao;
import com.mymacros.web.config.RootContextConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by Tomas on 26/07/2016.
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController
{
     @Inject
     private UserAndProfileServiceDao userAndProfileServiceDao;
     /**
      * Obtiene el formulario de login
      * @param model objeto que alamacena los elemeto a enviar a la vista
      * @return retorna la url de la vista.
      */
     @RequestMapping(value = "/form", method = RequestMethod.GET)
     public String getLogin(Model model)
     {
          RootContextConfig.message.info("\n\nUrl: /formn\n\n");
          model.addAttribute("loginDto", new LoginDto());
          return "login/session";
     }

     /**
      * Session es el metodo que valida el formulario enviado por el usuario
      * envia los errores obtenidos de las validaciones de las anotaciones de hibernate
      * y identifica si el usuario esta registrado si no, lo redirecciona a la pagina de registro.
      *
      * @param loginDto Almacena los datos del formulario login
      * @param bindingResult Representa las respuestas a los errores encontrados tras la validacion
      * @param model modelo que representa el envio de los atributos de este metod
      * @return retorna url de la vista
      */
     @RequestMapping(value = "/session", method = RequestMethod.POST)
     public String session(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                           BindingResult bindingResult, Model model, HttpServletRequest request)
     {
          if (bindingResult.hasErrors())
          {
               RootContextConfig.message.info("\n\nError de formularion\n\n");
               model.addAttribute("loginDto", loginDto);
               return "login/session";
          }
          RootContextConfig.message.info("\n\nuser: "+loginDto.getEmail());
          RootContextConfig.message.info("\n\nPass: "+loginDto.getPassword());
          if(this.userAndProfileServiceDao.loginUser(loginDto))
          {
               RootContextConfig.message.info("\n\nLogin aceptado \n\n");
               HttpSession session = request.getSession();
               session.setAttribute("id", UUID.nameUUIDFromBytes(loginDto.getEmail().getBytes()));
               session.setAttribute("email", loginDto.getEmail());
               session.setAttribute("idUser", this.userAndProfileServiceDao.getUser(loginDto.getEmail()));
               request.changeSessionId();
               RootContextConfig.message.info("\n\nSession creada \n\n");
               return "redirect:/app/daily/list";

          }else
          {
               RootContextConfig.message.info("\n\nSession no creada \n\n");
               model.addAttribute("message",1);
               return "login/session";
          }

     }
     /**
      * Metodo add que envia els formulario a la vista
      * @param model modelo alamacena el objeto que representa el formulario
      * @return retorna la url del formulario
      */
     @RequestMapping(value = "/add", method = RequestMethod.GET)
     public String addUser(Model model)
     {
          model.addAttribute("userAndProfileFormDto", new UserAndProfileFormDto());
          return "login/add";
     }

     /**
      * Agrega un nuevo elemeto al repositorio
      * @param userAndProfileFormDto Representa al formulario.
      * @param bindingResult Representa los errores del formulario obtenidos tras la validacion
      * @param model Contiene almacena los errores obtenidos en la validacion
      * @return retorna la vista daily si no tubo errores pero si encontro retorna la del formulario
      */
     @RequestMapping(value = "/add", method = RequestMethod.POST)
     public String addUser(@Valid @ModelAttribute("userAndProfileFormDto")
                                   UserAndProfileFormDto userAndProfileFormDto,
                           BindingResult bindingResult, Model model)
     {
          if (bindingResult.hasErrors())
          {
               model.addAttribute("userAndProfileFormDto", userAndProfileFormDto);
               return "login/add";
          }

          if (!userAndProfileFormDto.getPassword().equals(userAndProfileFormDto.getRepitPassword()))
          {
               RootContextConfig.message.info("\n\n Pass Incorrecto \n\n");
               model.addAttribute("userAndProfileFormDto", userAndProfileFormDto);
               model.addAttribute("passErr",1);
               return "login/add";
          }
          RootContextConfig.message.info("\n\nuser: "+userAndProfileFormDto.getEmail());
          RootContextConfig.message.info("\n\nPass: "+userAndProfileFormDto.getPassword());
          RootContextConfig.message.info("\n\nPass: "+userAndProfileFormDto.getRepitPassword());
          this.userAndProfileServiceDao.addUserAndProfile(userAndProfileFormDto);
          return "redirect:/login/form";
     }
}