import Engine.Entity.LivingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import Engine.Entity.Player;
import Engine.Inventory;
import Utility.Window;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class LivingEntityTest
{
    @Mock
    LivingEntity livingEntity;
    

    @Test
    void checkHealthDecrease()
    {
        LivingEntity livingEntity = Mockito.mock(LivingEntity.class);
        // Call the method on the mock
        livingEntity.decreaseHealth(0);
        // Verify that the method was called
        Mockito.verify(livingEntity, Mockito.times(1)).decreaseHealth(0);
    }

    @Test
    void checkLivingEntityHealth_ReturnsZero()
    {
        LivingEntity livingEntity = Mockito.mock(LivingEntity.class);
        when(livingEntity.getHealth()).thenReturn(0);

        assertEquals(0, livingEntity.getHealth());
        Mockito.verify(livingEntity, Mockito.times(1)).getHealth();
    }
}
