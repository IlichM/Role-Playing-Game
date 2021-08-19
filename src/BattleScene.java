public class BattleScene {
    // метод который вызывается в начале боя, са передаем параметры героя, монстра, которые встали у него на пути
    public void fight(CharacterParametrs hero,CharacterParametrs monster,Realm.FightCallback fightCallback) {
        //ходы будут идти в отдельном потоке
        Runnable runnable = ()->{
            //сюда записывается ход по счету
            int turn = 1;
            //когда бой будет закончен мы
            boolean isFightEnded = false;
            while (!isFightEnded) {
                System.out.println("----Ход: " + turn + "----" );
                //войны бьют по очереди, поэтому здесь мы описываем логику смены сторон
                if(turn++%2!=0) {
                    isFightEnded = makeHit(monster,hero,fightCallback);
                } else {
                    isFightEnded = makeHit(hero,monster,fightCallback);
                }
                try {
                    //чтобы бой не проходил за секунду, сделам эмитацию работы, как если бы у нас была анимация
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //запускаем новый поток
        Thread thread = new Thread(runnable);
        thread.start();
    }

    //метод для совершения удара
    public boolean makeHit(CharacterParametrs defender,CharacterParametrs attacker,Realm.FightCallback fightCallback) {
        //получаем силу удара
        int hit = attacker.attack();
        //отнимаем количество урона из здоровья защищающегося
        int defenderHealth = defender.getHealth() - hit;
        //если атака прошла, выводим в консоль сообщение об этом
        if(hit!=0) {
            System.out.println(String.format(" %s Нанес удар в %d единиц ",attacker.getName(),hit));
            System.out.println(String.format("У %s осталось %d единиц здоровья...",defender.getName(),defenderHealth));
        } else {
            //если атакующий промахнулся, т.е. урон не 0, выводим сообщение
            System.out.println(String.format(" %s промахнулся! ",attacker));
        }
        if(defenderHealth <= 0 && defender instanceof Hero) {
            //если здоровье меньше 0 и защищающий был герой, то игра заканчивается
            System.out.println("Извините, Вы пали в бою...");
            //вызываем коллбэк, что мы проиграли
            fightCallback.fightLost();
            return true;
        } else if(defenderHealth <= 0) {
            //если здоровья больше нет и защищающий - монстр, то забираем от монстра его опыт и золото
            System.out.println(String.format("Враг повержен! Вы получаете %d опыт и %d золото",defender.getXp(),defender.getGold()));
            attacker.setXp(attacker.getXp() + defender.getXp());
            attacker.setGold(attacker.getGold() + defender.getGold());
            //вызываем коллбэк, что мы победили
            fightCallback.fightWin();
            return true;
        } else {
            //если защизающий не повержен, то устанавливаем ему новый уровень здоровья
            defender.setHealth(defenderHealth);
            return false;
        }
    }
}
