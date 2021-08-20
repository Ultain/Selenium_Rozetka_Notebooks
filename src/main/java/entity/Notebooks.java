package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Notebooks {

    private int NotebookID;
    private String ModelName;
    private String Screen;
    private String Processor;
    private String RAM;
    private String Storage;
    private String VideoCard;
    private String Weight;

    public Notebooks(String modelName, String screen, String processor, String RAM, String storage, String videoCard, String weight) {
        ModelName = modelName;
        Screen = screen;
        Processor = processor;
        this.RAM = RAM;
        Storage = storage;
        VideoCard = videoCard;
        Weight = weight;
    }
}

