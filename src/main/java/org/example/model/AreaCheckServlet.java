package org.example.model;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jetty.client.api.Response;
import org.example.model.common.customException.ValidException;
import org.example.model.common.models.RequestEntryModel;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "AreaCheckServlet", value = "/areaCheck")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime;
        String result;
        boolean resultOfCalc = false;
        try {
            startTime = System.currentTimeMillis();
            var params = new ParsParams(new HashMap<>(){{
                put("x", req.getParameter("x"));
                put("y", req.getParameter("y"));
                put("r", req.getParameter("r"));
            }});
            resultOfCalc = calculate(params.getX(), params.getY(), params.getR());

            if (resultOfCalc) result = "Попал";
            else result = "Мимо";
            RequestEntryModel model = new RequestEntryModel(params.getX(), params.getY(), params.getR(), resultOfCalc, System.currentTimeMillis() - startTime, new Date());
            req.setAttribute("entry", model);
        } catch (ValidException e) {
            result = e.getMessage();
            RequestEntryModel model = null;
            req.setAttribute("entry", model);
        }
        req.setAttribute("result", result);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Получение контента из JSP

//            String resultJSP = StringEscapeUtils.escapeHtml4(renderJSPToString(req, resp, "/sample/resultSample.jsp"));
//            String trJSP = StringEscapeUtils.escapeHtml4(renderJSPToString(req, resp, "/sample/trSample.jsp"));
            PrintWriter out = resp.getWriter();

            String resultJSP = renderJSPToString(req, resp, "/sample/resultSample.jsp");
            String trJSP = renderJSPToString(req, resp, "/sample/trSample.jsp");

            // Формирование JSON-ответа
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("got", resultOfCalc);
            jsonResponse.put("resultJSP", resultJSP);
            jsonResponse.put("trJSP", trJSP);

            out.print(jsonResponse);

            out.flush();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            PrintWriter out = resp.getWriter();
            // Обработка ошибки
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", result);
            out.print(errorResponse);
            out.flush();
        }
    }

    private String renderJSPToString(HttpServletRequest request, HttpServletResponse response, String jspPath) throws ServletException, IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response) {
            @Override
            public PrintWriter getWriter() {
                return printWriter;
            }
        };
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
        dispatcher.include(request, responseWrapper);
        return stringWriter.toString();
    }
    private boolean calculate(Double x, Double y, Float r){

        if (x <= 0 && y <= 0)
            return false;
        if ((x <= 0 && y >= 0) && x * x + y * y > (r / 2) * (r / 2))
            return false;
        if ((x >= 0 && y >= 0) && x > r/2 || y > r)
            return false;
        if ((x >= 0 && y <= 0) && x - y > r / 2)
            return false;

        return true;
    }
}
