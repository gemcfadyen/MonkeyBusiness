package server;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RootDirectoryExceptionTest {
    private IOException originalException;
    private RootDirectoryException exception;

    @Before
    public void setUp() throws Exception {
        originalException = new IOException();
        exception = new RootDirectoryException(originalException);
    }

    @Test
    public void clientSocketExceptionHasMessage() {
        assertThat(exception.getMessage(), is("Exception thrown whilst determining repository root directory"));
    }

    @Test
    public void clientSocketExceptionCause() {
        assertThat(exception.getCause(), is(originalException));
    }
}