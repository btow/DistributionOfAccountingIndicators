package ru.btow.controller;

import org.junit.Before;
import org.junit.Test;
import ru.btow.model.providers.DataProvider;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AccountingNodesTreeTest extends AccountingNodesTree {

    @Before
    public void setUp() throws Exception {
        super.dataProvider = mock(DataProvider.class);
    }

    @Test
    public void testOpenConnectionTest() {
    }

    @Test
    public void testCreateTreeOnRootTest() {
    }

    @Test
    public void testGetNodeOnIdTest() {
    }

    @Test
    public void testCreateNewNodeTest() {
    }

    @Test
    public void testIsLeafTest() {
    }

    @Test
    public void testGetAmountOfchildrensConsumptionTest() {
    }

    @Test
    public void testGetDifferenceInConsumptionWithchildrenTest() {
    }
}