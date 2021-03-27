package edu.ujn.ExpertMachine.controller;

import edu.ujn.ExpertMachine.machine.InferenceMachine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InferenceMachine machine = new InferenceMachine(this.getServletContext());
//        machine.loadNodesFromDataBase();
    }
}
