package com.example.harkkatyprojekti;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class MunicipalityDataRetriever {


    public void getData(String location) {
        // https://statfin.stat.fi/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode areas = null;

        try {
            areas = objectMapper.readTree(new URL("https://statfin.stat.fi/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(areas.toPrettyString());

        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();


        for (JsonNode node : areas.get("variables").get(1).get("values")) {
            System.out.println(node.toPrettyString());
            values.add(node.asText());

        }

        for (JsonNode node : areas.get("variables").get(1).get("valueTexts")) {
            keys.add(node.asText());


        }

        HashMap<String, String> municipalityCodes = new HashMap<>();

        for(int i = 0; i < keys.size(); i++) {
            municipalityCodes.put(keys.get(i), values.get(i));
        }

        //System.out.println(municipalityCodes.toString());

        Scanner sc = new Scanner(System.in);
        String municipality = "";
        String code = null;

        while(true) {
            code = null;
            System.out.println("Anna kunnan nimi: ");
            municipality = sc.nextLine();
            code = municipalityCodes.get(municipality);

            if (code == null) {
                break;
            }

            try {
                URL url = new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/synt/statfin_synt_pxt_12dy.px");

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                JsonNode jsonInputString = objectMapper.readTree(new File("query.json"));

                ((ObjectNode) jsonInputString.get("query").get(0).get("selection")).putArray("values").add(code);

                byte[] input = objectMapper.writeValueAsBytes(jsonInputString);
                OutputStream os = con.getOutputStream();
                os.write(input, 0, input.length);

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    response.append(line.trim());
                }

                JsonNode municipalityData = objectMapper.readTree(response.toString());

                //System.out.println(municipalityData.toPrettyString());

                ArrayList<String> years = new ArrayList<>();
                ArrayList<String> populations = new ArrayList<>();

                for (JsonNode node : municipalityData.get("dimension").get("Vuosi").get("category").get("label")) {
                    years.add(node.asText());
                }

                for (JsonNode node : municipalityData.get("value")) {
                    populations.add(node.asText());
                }

                ArrayList<MunicipalityData> populationData = new ArrayList<>();

                for(int i = 0; i < years.size(); i++) {
                    populationData.add(new MunicipalityData(Integer.valueOf(years.get(i)), Integer.valueOf(populations.get(i))));
                }

                System.out.println("=======================");
                System.out.println(municipality);
                System.out.println("-----------------------");

                for (MunicipalityData data : populationData) {
                    System.out.print(data.getYear() + ": " + data.getPopulation() + "  ");
                    for(int i = 0; i < data.getPopulation() / 10000; i++) {
                        System.out.print("*");
                    }
                    System.out.println();
                }




            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }



        }
        sc.close();


    }
}
