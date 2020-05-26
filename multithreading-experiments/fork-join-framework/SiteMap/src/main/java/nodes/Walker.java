package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class Walker extends RecursiveTask<Set<String>> {

    private Parser parser;

    public Walker(Parser parser) {
        this.parser = parser;
    }

    @Override
    protected Set<String> compute() {

        // передается значение
        Set<String> links = parser.getLinks();
        List<Walker> taskList = new ArrayList<>();

        try {
            // переборка "детей", ответвление задач
            parser.getChildren().forEach(child -> {
                Walker task = new Walker(child);
                task.fork();
                taskList.add(task);
            });

            // переборка задач, добавление значения
            taskList.forEach(task -> links.addAll(task.join()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return links;
    }
}
