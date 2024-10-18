package com.example.EcoTrack;
import java.time.LocalDateTime;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usage")
public class Usage {
  
   private String email;
    private double waterUsage;
    private double electricityUsage;

    // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime localDateTime;
    // private double totalWaterUsage;
    // private double totalElectricityUsage;

    public double getWaterUsage() {
      return this.waterUsage;
    }
    public void setWaterUsage(double value) {
      this.waterUsage = value;
    }

    public double getElectricityUsage() {
      return this.electricityUsage;
    }
    public void setElectricityUsage(double value) {
      this.electricityUsage = value;
    }

    public String getEmail() {
      return this.email;
    }
    public void setEmail(String value) {
      this.email = value;
    }
    public LocalDateTime getLocalDateTime() {
      return this.localDateTime;
    }
    public void setLocalDateTime(LocalDateTime value) {
      this.localDateTime = value;
    }
//   class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
//         @Override
//         public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
//                 throws IOException, JsonProcessingException {
//             String date = p.getText();
//             // Convert the string with 'Z' to LocalDateTime
//             return ZonedDateTime.parse(date).toLocalDateTime();
//         }
// }
}
