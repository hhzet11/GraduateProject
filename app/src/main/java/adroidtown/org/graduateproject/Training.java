package adroidtown.org.graduateproject;

public class Training {
    public String trainingName;
    public String trainingCalorie;
    public String trainingURL;

    public Training(){

    }

    public Training(String trainingName, String trainingCalorie, String trainingURL){
        this.trainingName = trainingName;
        this.trainingCalorie = trainingCalorie;
        this.trainingURL = trainingURL;
    }

    public String getTrainingName(){ return trainingName; }

    public void setTrainingName(){ this.trainingName = trainingName; }

    public String getTrainingCalorie() { return trainingCalorie; }

    public void setTrainingCalorie(){ this.trainingCalorie = trainingCalorie; }

    public String getTrainingURL() { return trainingURL; }

    public void setTrainingURL(){ this.trainingURL = trainingURL; }

    public String toString(){
        return "Training{" + "trainingName='" + trainingName +'\'' + ",trainingCalorie='" + trainingCalorie + '\'' +
                ", trainingURL='" + trainingURL + '\'' + '}';
    }
}
