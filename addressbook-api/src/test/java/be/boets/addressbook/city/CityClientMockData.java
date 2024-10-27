package be.boets.addressbook.city;

public class CityClientMockData {

    public static String getSubCity() {
        return """
               [{"Postcode":{"postcode_hoofdgemeente":"3270","naam_hoofdgemeente":"Scherpenheuvel-Zichem","postcode_deelgemeente":"3271","naam_deelgemeente":"Averbode","taal":"N","region":"VL","longitude":"4.9806860000","latitude":"51.0261820000"}},{"Postcode":{"postcode_hoofdgemeente":"3270","naam_hoofdgemeente":"Scherpenheuvel-Zichem","postcode_deelgemeente":"3271","naam_deelgemeente":"Zichem","taal":"N","region":"VL","longitude":"4.9838940000","latitude":"51.0014870000"}}] 
                """;
    }
    public static String getMainCity() {
        return """
               [{"Postcode":{"postcode_hoofdgemeente":"3270","naam_hoofdgemeente":"Scherpenheuvel-Zichem","postcode_deelgemeente":"3270","naam_deelgemeente":"Scherpenheuvel","taal":"N","region":"VL","longitude":"4.9769580000","latitude":"50.9802320000"}},{"Postcode":{"postcode_hoofdgemeente":"3270","naam_hoofdgemeente":"Scherpenheuvel-Zichem","postcode_deelgemeente":"3270","naam_deelgemeente":"Scherpenheuvel-Zichem","taal":"N","region":"VL","longitude":"4.9769580000","latitude":"50.9802320000"}},{"Postcode":{"postcode_hoofdgemeente":"3270","naam_hoofdgemeente":"Scherpenheuvel-Zichem","postcode_deelgemeente":"3271","naam_deelgemeente":"Averbode","taal":"N","region":"VL","longitude":"4.9806860000","latitude":"51.0261820000"}},{"Postcode":{"postcode_hoofdgemeente":"3270","naam_hoofdgemeente":"Scherpenheuvel-Zichem","postcode_deelgemeente":"3271","naam_deelgemeente":"Zichem","taal":"N","region":"VL","longitude":"4.9838940000","latitude":"51.0014870000"}},{"Postcode":{"postcode_hoofdgemeente":"3270","naam_hoofdgemeente":"Scherpenheuvel-Zichem","postcode_deelgemeente":"3272","naam_deelgemeente":"Messelbroek","taal":"N","region":"VL","longitude":"4.9374410000","latitude":"50.9957570000"}},{"Postcode":{"postcode_hoofdgemeente":"3270","naam_hoofdgemeente":"Scherpenheuvel-Zichem","postcode_deelgemeente":"3272","naam_deelgemeente":"Testelt","taal":"N","region":"VL","longitude":"4.9518670000","latitude":"51.0087080000"}}]
                """;
    }
}
