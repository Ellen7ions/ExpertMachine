package edu.ujn.ExpertMachine.controller;

import edu.ujn.ExpertMachine.machine.InferenceMachine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SetInputServlet", value = "/setInputServlet")
public class SetInputServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        BufferedReader reader = request.getReader();
        StringBuilder builder = new StringBuilder();
        String temp;
        while ((temp = reader.readLine()) != null) {
            builder.append(temp);
        }

        String json = builder.toString();


        HttpSession session = request.getSession();
        InferenceMachine machine = (InferenceMachine) session.getAttribute("machine");
        if (machine == null) {
            machine = new InferenceMachine(this.getServletContext());
            session.setAttribute("machine", machine);
        }

        machine.run(json);

        writer.println(machine.sendGraphJSON());

        writer.close();
    }
}
