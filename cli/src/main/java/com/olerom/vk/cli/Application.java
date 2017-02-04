package com.olerom.vk.cli;

import com.olerom.vk.core.VkAdapter;
import com.olerom.vk.core.VkGraph;
import com.olerom.vk.core.VkRequest;
import com.vk.api.sdk.objects.friends.UserXtrLists;

import java.util.List;
import java.util.Scanner;

/**
 * Created by olerom on 01.02.17.
 */
public class Application {
    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        System.out.println("CLI: vk_friends graph");
        VkRequest vkRequest = new VkRequest();

        System.out.println("You can accept permissions at: ");
        System.out.println(vkRequest.getAuthURL());

        System.out.println("Enter code value: ");
        System.out.print(">>");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.next();

        VkAdapter vk = null;
        try {
            vk = new VkAdapter(code);
        } catch (Exception e) {
            System.out.println("Problem with creating VkAdapter, check code value.");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            List<UserXtrLists> myFriends = vk.getFriends();
            VkGraph graph = new VkGraph();
            int i = 0;

            for (UserXtrLists friend : myFriends) {
                graph.addVertex(friend);
            }

            for (UserXtrLists friend : myFriends) {
                List<Integer> id = vk.getMutalz(friend.getId());
                for (int ok : id) {
                    graph.addEdge(friend, vk.getActualFriend(ok));
                }

//                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append(i++ + ". " + friend.getFirstName() + " " + friend.getLastName() + " has friends: ");
//                List<Integer> ids = vk.getMutalz(friend.getId());
//                for (int ok : ids) {
//                    UserXtrLists kek = vk.getActualFriend(ok);
//                    stringBuilder.append(kek.getFirstName() + " " + kek.getLastName() + ", ");
//                }
                Thread.sleep(500);
            }

            System.out.println(graph);

            vk.test();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void help() {
    }
}
