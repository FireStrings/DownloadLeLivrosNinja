/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leolira.business;

import com.leolira.ui.Principal;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author Downloader Lira
 */
public class Downloader implements Runnable {

    private String urlRaiz = "http://baixar-download.jegueajato.com/";
    private List<String> badLinks;

    public Downloader() {

        this.inictialize();

    }

    private void inictialize() {

        this.badLinks = new ArrayList();

        this.badLinks.add(".ftpquota");
        this.badLinks.add(".htpasswds/");
        this.badLinks.add("404.shtml");
        this.badLinks.add("500.shtml");
        this.badLinks.add("cgi-bin/");
        this.badLinks.add("download.jegueajato.com/");
        this.badLinks.add("error_files/");
        this.badLinks.add("images/");
        this.badLinks.add("teste/");
        this.badLinks.add("/");

    }

    public void writeFile(String file, String name) throws IOException {
        boolean error = true;

        while (error) {
            try {
                URL url = new URL(file);

                FileUtils.copyURLToFile(url, new File("C:\\Livros\\" + name));
                System.out.println("Ultimo livro: " + name);   
                error = false;
                
            } catch (Exception e) {

                e.printStackTrace();                
            }
        }
    }

    public String getUrlSource(String url) throws IOException {
        URL urlCode = new URL(url);
        URLConnection yc = urlCode.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            a.append(inputLine);
        }
        in.close();

        return a.toString();
    }

    public Connection getConnection(String url) {

        return Jsoup.connect(url);
    }

    public void saveBook(String text) {

        Document doc = null;
        boolean error = true;
        try {

            while (error) {
                try {
                    doc = Jsoup.connect(text).get();
                    error = false;
                } catch (Exception e) {

                    e.printStackTrace();
                    
                }

            }
            for (Element file : doc.select("a")) {
                String linkAtual = file.attr("href");

                if (!badLinks.contains(linkAtual) && !text.contains(linkAtual)) {

                    if (linkAtual.contains(".pdf")) {

                        writeFile(text + linkAtual, linkAtual.replace("%20", " "));
                        break;

                    } else if (linkAtual.charAt(linkAtual.length() - 1) == '/') {

                        saveBook(text + linkAtual);

                    }
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getUrlRaiz() {
        return urlRaiz;
    }

    public void setUrlRaiz(String urlRaiz) {
        this.urlRaiz = urlRaiz;
    }

    @Override
    public void run() {
        this.saveBook(urlRaiz);
        
        JOptionPane.showMessageDialog(null, "Download concluído!");
        Principal.getWindows()[0].dispose();
    }
}
