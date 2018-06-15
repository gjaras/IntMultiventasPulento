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
    
    def zomatoWSCall(){
        // Se busca por Santiago de Chile (entity_id = 83, entity_type=city) y tipo de comida chilena (cuisines=229)
        def offset = 10
        def start = 0
        def request
        def response
        def parsed_json
        def searching = true
        def rest_count = 0
        def total_price = 0
        def total_average = 0
        def returnMap = [:]
        while(searching) {
            request = ("https://developers.zomato.com/api/v2.1/search?enitity_id=83&entity_type=city&cuisines=229&start=${start}&count=10").toURL()
            try{
                response = request.getText(requestProperties: ['user-key': '94c7209f2f9c7efadefea113f8b3f3b6'])
                println response
                parsed_json = new JsonSlurper().parseText(response)
                if (parsed_json.restaurants.size()>0){
                    parsed_json.restaurants.each{ res ->
                        rest_count++
                        total_price += res.restaurant.average_cost_for_two
                        println " id ${res.restaurant.id} restoran ${res.restaurant.name} es \$${res.restaurant.average_cost_for_two}"
                    }
                    start += offset
                }else{
                    searching = false
                }
            }catch(Exception e){
               searching = false
            }
        }
        total_average = (total_price > 0 && rest_count > 0) ? total_price.div(rest_count) : 0
        returnMap << [tres: rest_count]
        returnMap << [taver: total_average]
        
        return returnMap
    }
    
    def requestIngredientes(){
        def request = ("http://localhost:81/integracionwebservice/testws").toURL()
        
        def parsed_json = new JsonSlurper().parseText(response)
        parsed_json
    }
}

