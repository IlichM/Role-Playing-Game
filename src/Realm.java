import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//import static java.lang.Compiler.command;

public class Realm {
    //класс для чтения введенных строк из консоли
    private static BufferedReader br;
    //игрок должен храниться на протяжении всей игры
    private static CharacterParametrs player = null;
    //класс битвы можно не создавать каждый раз, а переиспользовать
    private static BattleScene battleScene = null;

    public static void main(String[] args) {
        //инициализируем BufferedReader
        br = new BufferedReader(new InputStreamReader(System.in));
        //инициализируем класс для боя
        battleScene = new BattleScene();
        //первое, что нужно сделать в начале игры - это создать персонажа
        System.out.println("Введите имя персонажа:");
        //далее ждем ввод от пользователя
        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {
        //если это первый запуск, то мы должны создать игрока, именем будет служить первая введенная строка из консоли
        if(player == null) {
            player = new Hero(string,100,20,20,0,0);
            System.out.println(String.format("Спасти наш мир вызвался %s! Да будет его броня крепка и" +
                    " битцепс кругл!", player.getName()));
            //метод для вывода меню
            printNavigation();
        }
        //варианты для команд
        switch (string) {
            case "1": {
                System.out.println("Торговец еще не приехал");
                command(br.readLine());
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3": {
                System.exit(1);
            }
            break;
            case "да":
                command("2");
                break;
            case "нет": {
                printNavigation();
                command(br.readLine());
            }
        }
        //снова ждем команды от пользователя
        command(br.readLine());
    }

    private static void printNavigation() {
        System.out.println("Куда вы хотите пойти:");
        System.out.println("1. К торговцу");
        System.out.println("2. В темный лес");
        System.out.println("3. Выход");
    }

    private static void commitFight() {
        battleScene.fight(player,createMonster(),new FightCallback(){
            @Override
            public void fightWin(){
                System.out.println(String.format("%s победил! Теперь у Вас %d опыта и %d золота," +
                " а также осталось %d единиц здоровья.",player.getName(),player.getXp(),player.getGold(),player.getHealth()));
                System.out.println("Хотите продолжить поход или вернуться в город? да/нет");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void fightLost() {

            }
        });
    }
    interface FightCallback {
        void fightWin();
        void fightLost();
    }

    private static CharacterParametrs createMonster() {
        //рандомайзер
        int random = (int)(Math.random()*10);
        //с вероятностью 50% создается скелет или гоблин
        if(random %2 == 0) {
            return new Goblin("Гоблин",50,10,10,100,20);
        } else {
            return new Skeleton("Скелет",25,20,20,100,10);
        }
    }
}
