package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;

  @BeforeEach
  public void init(){
    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    //verifying mock: getTorpedoCount was called once (for primary) or 0 times (for secondary)
    
    verify(mockPrimary, times(1)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(0)).fire(1);
    verify(mockSecondary, times(0)).isEmpty();
    //assertEquals(true, result);
    

  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true).thenReturn(false);
    when(mockPrimary.isEmpty()).thenReturn(false).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(false).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(0)).fire(1);
    verify(mockPrimary, times(2)).isEmpty();
    verify(mockSecondary, times(1)).fire(1);
    verify(mockSecondary, times(2)).isEmpty();
    //assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_1(){
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.getTorpedoCount()).thenReturn(1);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);
    
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_2(){
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockPrimary.getTorpedoCount()).thenReturn(1);
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.getTorpedoCount()).thenReturn(1);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);
    
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(0)).isEmpty();
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_3(){
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockPrimary.getTorpedoCount()).thenReturn(0);
    when(mockPrimary.fire(1)).thenReturn(false);
    when(mockSecondary.getTorpedoCount()).thenReturn(0);
    when(mockSecondary.fire(1)).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);
    
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_1(){
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockPrimary.getTorpedoCount()).thenReturn(0);
    when(mockPrimary.fire(1)).thenReturn(false);
    when(mockSecondary.getTorpedoCount()).thenReturn(0);
    when(mockSecondary.fire(1)).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);
    
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false, result);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();
    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_2(){
    when(mockPrimary.fire(1)).thenReturn(true).thenReturn(false);
    when(mockPrimary.isEmpty()).thenReturn(false).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(false).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(0)).fire(1);
    verify(mockPrimary, times(2)).isEmpty();
    verify(mockSecondary, times(1)).fire(1);
    verify(mockSecondary, times(2)).isEmpty();
    assert(result);
  }

  @Test
  public void fireTorpedo_Single_sourcecode(){
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assert(result);
    verify(mockSecondary, times(0)).isEmpty();
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockPrimary, times(1)).fire(1);
  }

  //Tesztesetek a kódfedettség javítása érdekében:
  @Test
  public void fireTorpedo_All_firePrimary(){
    when(mockPrimary.fire(1)).thenReturn(true).thenReturn(false);
    when(mockPrimary.isEmpty()).thenReturn(false).thenReturn(false).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assert(result);
    verify(mockSecondary, times(1)).isEmpty();
    verify(mockPrimary, times(3)).isEmpty();
    verify(mockPrimary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_primaryFiredLast(){
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();
    assert(result);
  }

  @Test
  public void fireTorpedo_Single_primaryFiredLastSecondaryEmpty(){
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(2)).fire(1);
    verify(mockPrimary, times(2)).isEmpty();
    verify(mockSecondary, times(0)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();
    assert(result);
  }

  @Test
  public void fireTorpedo_Single_primaryFiredLastSecondaryAndThenPrimaryEmpty(){
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false).thenReturn(true).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockPrimary, times(2)).isEmpty();
    verify(mockSecondary, times(0)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();
    assert(!result);
  }

  @Test
  public void fireTorpedo_All_EmptyStores(){
    when(mockSecondary.isEmpty()).thenReturn(false).thenReturn(false).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockSecondary, times(1)).fire(1);
    verify(mockSecondary, times(3)).isEmpty();
    verify(mockPrimary, times(0)).fire(1);
    verify(mockPrimary, times(2)).isEmpty();
    assert(result);
  }

}
