package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/22
 * @Description
 */
public class ProblemInfo {
//    private final String id;
    private final String title;
    private final String description;

    public ProblemInfo( String title, String description) {
//        this.id = id;
        this.title = title;
        this.description = description;
    }

//    public String getId() {
//        return id;
//    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
