package jp.aymandev.astolfo.rpg.pets.talents;

import jp.aymandev.astolfo.rpg.pets.RPGPet;
import jp.aymandev.astolfo.rpg.player.EnumCharacteristic;

import java.util.HashMap;

/**
 * Created by AymanDev on 01.08.2018.
 */
public class PetTalentList {

    public static final HashMap<String, PetTalent> pesikTalents = new HashMap<>();
    public static final HashMap<String, PetTalent> bearTalents = new HashMap<>();
    public static final HashMap<String, PetTalent> falconTalents = new HashMap<>();
    public static final HashMap<String, PetTalent> turtleTalents = new HashMap<>();
    public static final HashMap<String, PetTalent> wolfTalents = new HashMap<>();
    public static final HashMap<String, PetTalent> ghostWolfTalents = new HashMap<>();

    public PetTalentList() {
        pesikTalents();
        ghostWolfTalents();
        bearTalents();
        falconTalents();
        turtleTalents();
        wolfTalents();
    }

    public static PetTalent getTalent(String localizedName, String key) {
        switch (localizedName) {
            case "Пёсик":
                return pesikTalents.get(key);
            case "Призрачный волк":
                return ghostWolfTalents.get(key);
            case "Волк":
                return wolfTalents.get(key);
            case "Черепаха":
                return turtleTalents.get(key);
            case "Сокол":
                return falconTalents.get(key);
            case "Медведь":
                return bearTalents.get(key);
        }
        return null;
    }

    private void registerPetTalent(PetTalent petTalent, HashMap<String, PetTalent> map) {
        map.put(petTalent.getLocalizedName().toLowerCase(), petTalent);
    }

    private void ghostWolfTalents() {
        //Призрачный волк
        registerPetTalent(new PetTalent("Свирепый укус") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH);
                    }
                }.setDescription("Усиливает питомца"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Астральный скачок") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.INTELLIGENCE);
                    }
                }.setDescription("Усиливает питомца"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Команда взять") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH);
                    }
                }.setDescription("Усиливает питомца"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Исчезновение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 5);
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH) +
                                pet.getCharacteristic(EnumCharacteristic.INTELLIGENCE);
                    }
                }.setDescription("Усиливает питомца\n+5% к шансу побега"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Скачок за спину") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH) +
                                pet.getCharacteristic(EnumCharacteristic.INTELLIGENCE);
                    }
                }.setDescription("Усиливает питомца"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Улучшенная духовная связь") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setCharacteristic(EnumCharacteristic.INTELLIGENCE,
                                pet.getCharacteristic(EnumCharacteristic.INTELLIGENCE) + 4);
                    }
                }.setDescription("Интеллект: +4"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Свирепый рёв") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 2);
                    }
                }.setDescription("+2% к шансу побега"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Удар когтями") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.STRENGTH)
                                + pet.getCharacteristic(EnumCharacteristic.AGILITY) * 1.5));
                    }
                }.setDescription("Усиливает питомца"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Улучшенная духовная связь LVL.2") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.INTELLIGENCE, 4, false);
                        pet.addCharacteristic(EnumCharacteristic.INTELLIGENCE, 19, false);
                        pet.removeTalent(PetTalentList.ghostWolfTalents.get("улучшенная духовная связь"));
                    }
                }.setDescription("Интеллект: +19"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Волчий Вой") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 5);
                    }
                }.setDescription("Шанс побега: +5%"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Преследование цели") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.INTELLIGENCE) * 2;
                    }
                }.setDescription("Усиление питомца"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Увеличение в размерах") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.STRENGTH, 5, false);
                        pet.addCharacteristic(EnumCharacteristic.AGILITY, 5, false);
                    }
                }.setDescription("Сила: +5\nЛовкость: +5"), ghostWolfTalents
        );
        registerPetTalent(new PetTalent("Идеальная духовная связь LVL.3") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.INTELLIGENCE, -9, false);
                        pet.getRpgPlayer().addCharacteristic(EnumCharacteristic.INTELLIGENCE, 20);
                        pet.removeTalent(PetTalentList.ghostWolfTalents
                                .get("улучшенная духовная связь lvl.2"));
                    }
                }.setDescription("Интеллект: +10\nИнтеллект героя: +20"), ghostWolfTalents
        );
    }

    private void bearTalents() {
        //МЕДВЕД
        registerPetTalent(new PetTalent("Свирепый укус") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH);
                    }
                }.setDescription("Усиливает питомца"), bearTalents
        );
        registerPetTalent(new PetTalent("Таран") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.ENDURANCE) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH);
                    }
                }.setDescription("Усиливает питомца"), bearTalents
        );
        registerPetTalent(new PetTalent("Команда взять") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH);
                    }
                }.setDescription("Усиливает питомца"), bearTalents
        );
        registerPetTalent(new PetTalent("Стремительный рывок") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 1.3);
                    }
                }.setDescription("Усиливает питомца"), bearTalents
        );
        registerPetTalent(new PetTalent("Оглушение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 5);
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH);
                    }
                }.setDescription("Шанс побега: +5%\nУсиливает питомца"), bearTalents
        );
        registerPetTalent(new PetTalent("Свирепый рёв") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 2);
                    }
                }.setDescription("Шанс побега: +2%"), bearTalents
        );
        registerPetTalent(new PetTalent("Удар когтями") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 1.5);
                    }
                }.setDescription("Усиливает питомца"), bearTalents
        );
        registerPetTalent(new PetTalent("Отвлечение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff((int) (pet.getEscapeBuff() +
                                (pet.getCharacteristic(EnumCharacteristic.ENDURANCE) *
                                        0.5)));
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 2;
                    }
                }.setDescription("Усиливает питомца и шанс побега"), bearTalents
        );
        registerPetTalent(new PetTalent("Заострённые клыки") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) * 3;
                    }
                }.setDescription("Усиливает питомца и шанс побега"), bearTalents
        );
        registerPetTalent(new PetTalent("Отвлечение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.STRENGTH,
                                5, false);
                        pet.addCharacteristic(EnumCharacteristic.AGILITY,
                                15, false);
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 2;
                    }
                }.setDescription("Ловкость: +15\nСила: +5"), bearTalents
        );
        registerPetTalent(new PetTalent("Толстая шкура") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.STRENGTH,
                                5, false);
                        pet.addCharacteristic(EnumCharacteristic.AGILITY,
                                15, false);
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 2;
                    }
                }.setDescription("Выносливость увеличивается в 3 раза\n" +
                        "Сила увеличивается в 2 раза"), bearTalents
        );
    }

    private void falconTalents() {
        //ФЫЛКОН
        registerPetTalent(new PetTalent("Свирепый укус") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH) * 2);
                    }
                }.setDescription("Усиливает питомца"), falconTalents
        );
        registerPetTalent(new PetTalent("Воздушная разведка") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 10);
                    }
                }.setDescription("Шанс побега: +10%"), falconTalents
        );
        registerPetTalent(new PetTalent("Команда взять") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 2;
                    }
                }.setDescription("Усиливает питомца"), falconTalents
        );
        registerPetTalent(new PetTalent("Стремительный рывок") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 2.6);
                    }
                }.setDescription("Усиливает питомца"), falconTalents
        );
        registerPetTalent(new PetTalent("Острые когти") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) * 4;
                    }
                }.setDescription("Усиливает питомца"), falconTalents
        );
        registerPetTalent(new PetTalent("Ускорение"){
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 2);
                    }
                }
                .setDescription("Шанс побега: +2%"), falconTalents
        );
        registerPetTalent(new PetTalent("Увеличение в размерах") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.AGILITY,
                                20, false);
                    }
                }.setDescription("Усиливает питомца"), falconTalents
        );
        registerPetTalent(new PetTalent("Отвлечение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff((int) (pet.getEscapeBuff() +
                                (pet.getCharacteristic(EnumCharacteristic.ENDURANCE) *
                                        0.5)));
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 2;
                    }
                }.setDescription("Усиливает питомца и шанс побега"), falconTalents
        );
        registerPetTalent(new PetTalent("Соколиный взор") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.getRpgPlayer().addCharacteristic(EnumCharacteristic.AGILITY, 30);
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) * 3;
                    }
                }.setDescription("Усиливает питомца и шанс побега"), falconTalents
        );
    }

    private void turtleTalents() {
        //ЧЫРЫПАХА
        registerPetTalent(new PetTalent("Укус") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.STRENGTH) * 4;
                    }
                }.setDescription("Усиливает питомца"), turtleTalents
        );
        registerPetTalent(new PetTalent("Команда взять") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH) * 3);
                    }
                }.setDescription("Усиливает питомца"), turtleTalents
        );
        registerPetTalent(new PetTalent("Свирепый рёв") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 3);
                    }
                }.setDescription("Шанс побега: +3%"), turtleTalents
        );
        registerPetTalent(new PetTalent("Оглушение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 5);
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH);
                    }
                }.setDescription("Шанс побега: +5%\nУсиливает питомца"), turtleTalents
        );
        registerPetTalent(new PetTalent("Отвлечение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff((int) (pet.getEscapeBuff() +
                                (pet.getCharacteristic(EnumCharacteristic.ENDURANCE) * 0.5)));
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.STRENGTH) * 4;
                    }
                }.setDescription("Усиливает питомца и шанс побега"), turtleTalents
        );
        registerPetTalent(new PetTalent("Увеличение в размерах") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.STRENGTH, 20, false);
                    }
                }.setDescription("Сила: +20"), turtleTalents
        );
        registerPetTalent(new PetTalent("Крепкий панцирь") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 10);
                    }
                }.setDescription("Шанс побега: +10%\n" +
                        "Питомец не умирает в случае побега"), turtleTalents
        );
    }

    private void wolfTalents() {
        //ВЫЛК
        registerPetTalent(new PetTalent("Свирепый укус") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.STRENGTH) +
                                pet.getCharacteristic(EnumCharacteristic.AGILITY);
                    }
                }.setDescription("Усиливает питомца"), wolfTalents
        );
        registerPetTalent(new PetTalent("Острые когти") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY);
                    }
                }.setDescription("Усиливает питомца"), wolfTalents
        );
        registerPetTalent(new PetTalent("Команда взять") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.STRENGTH) +
                                pet.getCharacteristic(EnumCharacteristic.AGILITY);
                    }
                }.setDescription("Усиливает питомца"), wolfTalents
        );
        registerPetTalent(new PetTalent("Стремительный рывок") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) (pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH) * 1.3);
                    }
                }.setDescription("Усиливает питомца"), wolfTalents
        );
        registerPetTalent(new PetTalent("Оглушение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff((pet.getEscapeBuff() + 5));
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.STRENGTH) +
                                pet.getCharacteristic(EnumCharacteristic.AGILITY);
                    }
                }.setDescription("Усиливает питомца и шанс побега"), wolfTalents
        );
        registerPetTalent(new PetTalent("Свирепый рёв") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 2);
                    }
                }.setDescription("Шанс побега: +2%"), wolfTalents
        );
        registerPetTalent(new PetTalent("Удар когтями") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.STRENGTH) +
                                pet.getCharacteristic(EnumCharacteristic.AGILITY)) * 1.5);
                    }
                }.setDescription("Усиливает питомца"), wolfTalents
        );
        registerPetTalent(new PetTalent("Отвлечение") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff((int) (pet.getEscapeBuff() +
                                (pet.getCharacteristic(EnumCharacteristic.STRENGTH) * 0.5)));
                    }

                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (pet.getCharacteristic(EnumCharacteristic.STRENGTH) +
                                pet.getCharacteristic(EnumCharacteristic.AGILITY)) * 2;
                    }
                }.setDescription("Усиливает питомца и шанс побег"), wolfTalents
        );
        registerPetTalent(new PetTalent("Волчий Вой") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff((pet.getEscapeBuff() + 5));
                    }
                }.setDescription("Шанс побега: +5%"), wolfTalents
        );
        registerPetTalent(new PetTalent("Заострённые клыки") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return pet.getCharacteristic(EnumCharacteristic.AGILITY) * 2;
                    }
                }.setDescription("Усиливает питомца"), wolfTalents
        );
        registerPetTalent(new PetTalent("Увеличение в размерах") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.STRENGTH,
                                20, false);
                        pet.addCharacteristic(EnumCharacteristic.AGILITY,
                                20, false);
                    }
                }.setDescription("Усиливает питомца"), wolfTalents
        );
    }

    private void pesikTalents() {
        //Пёсик
        registerPetTalent(new PetTalent("Милота") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 1);
                    }
                }.setDescription("Милый спутник, не так хорош как 2D тян\n(+1% к шансу побега)"), pesikTalents
        );
        registerPetTalent(new PetTalent("Укус") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 0.15);
                    }
                }.setDescription("Гавкает и агрит всех мобов в округе усиливая себя"), pesikTalents
        );
        registerPetTalent(new PetTalent("Команда взять") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 0.3);
                    }
                }.setDescription("Усиливает питомца"), pesikTalents
        );
        registerPetTalent(new PetTalent("Свирепый(очень) рёв") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 2);
                    }
                }.setDescription("(+2% к шансу побега)"), pesikTalents
        );
        registerPetTalent(new PetTalent("Бег за хвостом") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setCharacteristic(EnumCharacteristic.AGILITY,
                                pet.getCharacteristic(EnumCharacteristic.AGILITY) + 5);
                    }
                }.setDescription("Ловкость: +5"), pesikTalents
        );
        registerPetTalent(new PetTalent("Острые когти") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setCharacteristic(EnumCharacteristic.STRENGTH,
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH) + 5);
                    }
                }.setDescription("Сила: +5"), pesikTalents
        );
        registerPetTalent(new PetTalent("Разрывание цели") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.AGILITY) +
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH)) * 0.3);
                    }
                }.setDescription("Усиливает питомца"), pesikTalents
        );
        registerPetTalent(new PetTalent("Удар хвостом") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.STRENGTH) * 0.45));
                    }
                }.setDescription("Усиливает питомца"), pesikTalents
        );
        registerPetTalent(new PetTalent("Острые зубы") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setCharacteristic(EnumCharacteristic.STRENGTH,
                                pet.getCharacteristic(EnumCharacteristic.STRENGTH) + 3);
                        pet.setCharacteristic(EnumCharacteristic.AGILITY,
                                pet.getCharacteristic(EnumCharacteristic.AGILITY) + 5);
                    }
                }.setDescription("Сила: +3\nЛовкость: +5"), pesikTalents
        );
        registerPetTalent(new PetTalent("Преследованние цели") {
                    @Override
                    public int onTalentPowerCalculate(RPGPet pet) {
                        return (int) ((pet.getCharacteristic(EnumCharacteristic.AGILITY) * 0.45));
                    }
                }.setDescription("Усиливает питомца"), pesikTalents
        );
        registerPetTalent(new PetTalent("Верный друг") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.setEscapeBuff(pet.getEscapeBuff() + 5);
                    }
                }.setDescription("Шанс побега: +5%"), pesikTalents
        );
        registerPetTalent(new PetTalent("Увеличение в размерах") {
                    @Override
                    public void onTalentLearned(RPGPet pet) {
                        pet.addCharacteristic(EnumCharacteristic.STRENGTH, 10, false);
                        pet.addCharacteristic(EnumCharacteristic.AGILITY, 10, false);
                    }
                }.setDescription("Сила: +10\nЛовкость: +10"), pesikTalents
        );
        registerPetTalent(new PetTalent("Взросление")
                .setDescription("Сила: x3\nЛовкость: x3"), pesikTalents
        );
    }
}
