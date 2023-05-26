import Engine.Entity.Items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import Engine.Entity.Player;
import Engine.Inventory;
import Utility.Window;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class PlayerActionTest
{
    Player player;

    @Mock
    Window window;

    @Mock
    Inventory inventory;

    @Mock
    Item item;

    @InjectMocks
    Canvas canvas;


    @BeforeEach
    void setUp() throws Exception
    {
        var window = Mockito.mock(Window.class);
        var inventory = Mockito.mock(Inventory.class);
        var item = Mockito.mock(Item.class);


        player = new Player(window, 0, 0, inventory);
    }

    @Test
    void check_ifPlayerHasInventory()
    {
        when(player.getInventory()).thenReturn(inventory);
    }

//    @Test
//    check_ifPlayerTookItem_fromInventory()
//    {
//        List<Item> items = new ArrayList<>();
//        items.add(item);
//
//        when(player.getInventory())
//
//        boolean tookItem = player.takeItem();
//        assertFalse(tookItem);
//    }

    @Test
    void check_if_player_is_alive()
    {
//        boolean alive = player.alive();
//        assertFalse(player.alive());
    }

}
