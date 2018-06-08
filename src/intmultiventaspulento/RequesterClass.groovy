/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package intmultiventaspulento
import groovy.json.JsonSlurper

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
    
    def getImgBytes(country_name){
        def request = ("https://developers.zomato.com/api/v2.1/cities?q=${country_name.capitalize()}").toURL()
        def response = request.getText(requestProperties: ['user-key': 'fe72fbe77768492c61bdbf334d892f74'])
        println response
        def parsed_json = new JsonSlurper().parseText(response)
        if (parsed_json.location_suggestions.empty) {
            throw new Exception("Busqueda no devolvió resultados");
        }
        def img_url = parsed_json.location_suggestions[0].country_flag_url.replaceAll(java.util.regex.Pattern.quote('\\'),"")
        println img_url
        URL req = (img_url).toURL()
        def imgbytes = req.getBytes();
        println imgbytes
        return imgbytes
    }
}

