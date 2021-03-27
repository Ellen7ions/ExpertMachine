package edu.ujn.ExpertMachine.controller;

import edu.ujn.ExpertMachine.machine.InferenceMachine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetGraphServlet", value = "/getGraph")
public class GetGraphServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();

        InferenceMachine inferenceMachine = (InferenceMachine) session.getAttribute("machine");

        if (inferenceMachine == null) {
            InferenceMachine machine = new InferenceMachine(this.getServletContext());
            session.setAttribute("machine", machine);
            writer.println(machine.sendGraphJSON());
        } else {
            writer.println(inferenceMachine.sendGraphJSON());
        }

        writer.close();
    }
}
