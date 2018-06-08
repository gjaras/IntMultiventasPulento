/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package java.intmultiventaspulento

import groovy.json.JsonSlurper
import org.hibernate.Transaction
import pojos.Establishments

/**
 *
 * @author cetecom
 */
class RequesterClass {
//	
//    def performRequest(){
//        println("WORKING")
//        def request = ("https://developers.zomato.com/api/v2.1/cities?q=Santiago").toURL()
//        def response = request.getText(requestProperties: ['user-key': 'fe72fbe77768492c61bdbf334d892f74'])
//        
//        def parsed_json = new JsonSlurper().parseText(response)
//        println response
//        def city_id = parsed_json.location_suggestions[0].id
//        println "EL ID DE SANTIAGO DE CHILE ES: ${parsed_json.location_suggestions[0].id}"
//        
//        request = ("https://developers.zomato.com/api/v2.1/cities?city_ids=${city_id}").toURL()
//        response = request.getText(requestProperties: ['user-key': 'fe72fbe77768492c61bdbf334d892f74'])
//        
//        println response
//    }
    
    def getEstList(country_name){
        def request = ("https://developers.zomato.com/api/v2.1/cities?q=${country_name.capitalize()}").toURL()
        def response = request.getText(requestProperties: ['user-key': 'fe72fbe77768492c61bdbf334d892f74'])
        println response
        def parsed_json = new JsonSlurper().parseText(response)
        if (parsed_json.location_suggestions.empty) {
            throw new Exception("Busqueda no devolvió resultados");
        }
        
        def country_id = parsed_json.location_suggestions[0].id
        def est_url = ("https://developers.zomato.com/api/v2.1/establishments?city_id=${country_id}").toURL()
        def est_resp = est_url.getText(requestProperties: ['user-key': 'fe72fbe77768492c61bdbf334d892f74'])
        println est_resp
        parsed_json = new JsonSlurper().parseText(response)
        def sesion=HibernateUtil.getSessionFactory().openSession()
        sesion.save(marca);
        sesion.getTransaction().commit();
        sesion.flush()
        parsed_json.establishments.each{ est ->
            Establishments e = new Establishments(est.id, est.name)
            sesion.beginTransaction()
            def q = sesion.createQuery("from establishments where id=${est.id}")
            def result = q.uniqueResult()
            sesion.flush();
            if (result){
                sesion.update(e);
            }else{
                sesion.create(e);
            }
            sesion.getTransaction().commit()
            sesion.flush()
        }
        def q = sesion.createQuery("from establishments");
        def est = q.list();
        def result = []
        est.each{ st ->
            result << "id: ${st.id} name: ${st.name}"
        }
        sesion.flush();
        sesion.close();
        
        return result.toString()
    }
}

