package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.PasswordCriptography;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUser implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String pageJSP = "";

        //código da foto
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        User user = (User) req.getSession().getAttribute("user");
        User tempUser = new User();

        if (name.isEmpty()) {
            //erro
        }
        if (password.isEmpty()) {
            password = user.getUserPassword();
        } else {
            password = PasswordCriptography.generateMd5(password);
        }

        tempUser.setCodEmail(user.getCodEmail());
        tempUser.setUserName(name);
        tempUser.setUserPassword(password);
        tempUser.setCurrentTheme(user.getCurrentTheme());
        //tempUser.setUserPhoto(userPhoto); PEDRO

        IKeepUser keepUser = new KeepUser();
        boolean success = keepUser.updateUser(tempUser);
        if (!success) {
            //erro
        } else {
            pageJSP = "/configuracoes.jsp";
        }
        
        return pageJSP;
    }

}