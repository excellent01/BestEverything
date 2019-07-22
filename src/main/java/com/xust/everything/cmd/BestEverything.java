package com.xust.everything.cmd;
import com.xust.everything.config.Config;
import com.xust.everything.core.Manager;
import com.xust.everything.core.model.Condition;
import com.xust.everything.core.model.Thing;
import java.util.List;
import java.util.Scanner;

/**
 * CMD客户端
 * @auther plg
 * @date 2019/7/21 14:41
 */
public class BestEverything {
    private static Scanner scanner = new Scanner(System.in);
    private static Config config = Config.getInstance();
    private static Manager manager = Manager.getManager();
    public static void main(String[] args) {
       welcome();
       manager.start();
       function(manager);
    }
    private static void welcome() {
        System.out.println("欢迎使用，BestEveryThing");
    }
    private static void function(Manager manager){
        while(true){
            System.out.print(">>");
            String input = scanner.nextLine();
            if(input.startsWith("search")){
                // search name [file_type]
                String[] values = input.split(" ");
                if(values.length >= 2){
                    if(!values[0].equals("search")){
                        help();
                        continue;
                    }
                    Condition condition = new Condition();
                    String name = values[1];
                    condition.setName(name);
                    if(values.length >= 3){
                        String fileType = values[2];
                        condition.setFileType(fileType);
                    }
                    search(manager,condition);
                }else{
                    help();
                    continue;
                }
            }
            switch (input){
                case "help":
                    help();
                    break;
                case "quit":
                    quit();
                    return;
                case "index":
                    index(manager);
                    break;
                default:
                    help();
            }

        }
    }
    private static void index(Manager manager) {
        manager.buildIndex();
    }

    private static void quit() {
        System.out.println("再见。");
    }

    private static void help() {
        System.out.println("命令列表：");
        System.out.println("退出：quit");
        System.out.println("帮助：help");
        System.out.println("索引：index");
        System.out.println("搜素: search <name> [file-Type] img | doc | bin | archive | other");
    }

    private static void search(Manager manager,Condition condition){
        condition.setLimit(config.getMaxReturn());
        condition.setOrderByASC(config.getDeptOrderAsc());
        List<Thing> search = manager.search(condition);
        for(Thing thing : search){
            System.out.println(thing.getPath());
        }
    }
}
