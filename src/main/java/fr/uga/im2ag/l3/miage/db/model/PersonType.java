package fr.uga.im2ag.l3.miage.db.model;

public enum PersonType {
    STUDENT(Values.STUDENT), TEACHER(Values.TEACHER);

    private String type;

    public static class Values {
        public static final String TEACHER = "teacher";
        public static final String STUDENT = "student";
    }

    PersonType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
