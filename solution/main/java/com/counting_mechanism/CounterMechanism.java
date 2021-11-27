package com.counting_mechanism;

import com.springkafka.Model;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
public class CounterMechanism {

    private static LinkedList<Second> secondsLinkedList = new LinkedList<>();

    public static void insert(Model model){
        if(secondsLinkedList.isEmpty() || secondsLinkedList.getLast().getTs() < model.getTs()){
            secondsLinkedList.addLast(new Second(model.getTs(),model.getUid()));
        }
        else if(secondsLinkedList.getLast().getTs() == model.getTs()){
            if(secondsLinkedList.getLast().userIdExists(model.getUid()) == false){
                secondsLinkedList.getLast().addUserId(model.getUid());
            }
        }
        else if(secondsLinkedList.getLast().getTs() > model.getTs()){
            log.info("CounterMechanism::insert(model) - Late/random timestamp: " + model.getUid() + " | " + model.getTs());

        }
    }

    public static int getUsersPerSeconds(){

        System.out.println("------------ List size: " + secondsLinkedList.size() + " ------------");

        for (Second second: secondsLinkedList) {
            System.out.print("Ts: " + second.getTs() + " <=> ");
            System.out.print(second.getDateTs().getDate() + "." + second.getDateTs().getMonth() + "." + (second.getDateTs().getYear() + 1900) + ".");
            System.out.println(" | " + second.getDateTs().getHours() + ":" + second.getDateTs().getMinutes() + ":" + second.getDateTs().getSeconds());

            System.out.println("Number of unique users: " + second.getUserIdsHashSet().size());

            System.out.println("****************************************************");
        }

        System.out.println("------------ End of list ------------");

        return secondsLinkedList.size();
    }

    public static int getUniqueUsersBetweenTwoDates(int startUnixTime, int endUnixTime){

        HashSet<String> uniqueUsersHashSet = new HashSet<>();

        for (int i = 0; i < secondsLinkedList.size() && secondsLinkedList.get(i).getTs() <= endUnixTime; i++) {

            if(secondsLinkedList.get(i).getTs() >= startUnixTime){
                uniqueUsersHashSet.addAll(secondsLinkedList.get(i).getUserIdsHashSet());
            }
        }

        printUniqueUsersHashSet(uniqueUsersHashSet);

        return uniqueUsersHashSet.size();
    }

    private static void printUniqueUsersHashSet(HashSet<String> uniqueUsersHashSet){

        if(uniqueUsersHashSet.isEmpty()) return;

        System.out.println("Number of unique users: " + uniqueUsersHashSet.size());

        Iterator hashSetIterator = uniqueUsersHashSet.iterator();

        while (hashSetIterator.hasNext()){
            System.out.println(hashSetIterator.next());
        }
    }

    public static Map<Minute, HashSet<String>> groupUsersByMinutes(){

        Map<Minute, List<Second>> secondsToMinuteMap = secondsLinkedList.stream()
                .collect(groupingBy(second -> new Minute(second.getDateTs().getMinutes(), second.getDateTs().getHours(),
                second.getDateTs().getDate(), second.getDateTs().getMonth(), second.getDateTs().getYear() + 1900)));

        Map<Minute, HashSet<String>> userToMinuteMap = new HashMap<>();

        for (Minute minute: secondsToMinuteMap.keySet()){

            List<Second> secondsInMinute = secondsToMinuteMap.getOrDefault(minute, null);

            if(secondsInMinute != null){

                for (Second second: secondsInMinute) {
                    if(!userToMinuteMap.containsKey(minute)){
                        userToMinuteMap.put(minute, new HashSet<>());
                        userToMinuteMap.get(minute).addAll(second.getUserIdsHashSet());
                    }else{
                        userToMinuteMap.get(minute).addAll(second.getUserIdsHashSet());
                    }
                }
            }
        }

        printUsersToMinuteMap(userToMinuteMap);

        return userToMinuteMap;
    }

    private static void printUsersToMinuteMap(Map<Minute, HashSet<String>> userToMinuteMap){
        for (Minute minute: userToMinuteMap.keySet()) {
            HashSet<String> usersInMinute = userToMinuteMap.getOrDefault(minute, null);
            if (usersInMinute != null) {
                System.out.println(minute.toString() + " " + usersInMinute.size());
            }
        }
    }


}
