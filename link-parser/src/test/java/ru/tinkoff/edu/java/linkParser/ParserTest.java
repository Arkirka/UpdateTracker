package ru.tinkoff.edu.java.linkParser;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParserTest {

    @ParameterizedTest
    @MethodSource("parse_shouldReturnValidLink_whenLinkValidCases")
    public void parse_shouldReturnValidLink_whenLinkValid(String source, String expected) {
        assertEquals(Optional.of(expected), new Parser(source).parse());
    }

    public Stream<Arguments> parse_shouldReturnValidLink_whenLinkValidCases() {
        return Stream.of(
                Arguments.of(
                        "https://github.com/sanyarnd/tinkoff-java-course-2022/", "sanyarnd/tinkoff-java-course-2022"
                ),
                Arguments.of(
                        "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c", "1642028"
                ),
                Arguments.of(
                        "https://github.com/person/repo", "person/repo"
                ),
                Arguments.of(
                        "https://github.com/some/some1", "some/some1"
                ),
                Arguments.of(
                        "https://stackoverflow.com/questions/1234/some-theme", "1234"
                ),
                Arguments.of(
                        "https://stackoverflow.com/questions/12312312312/meme", "12312312312"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("parse_shouldReturnNotValid_whenLinkInvalidCases")
    public void parse_shouldReturnNotValid_whenLinkInvalid(String source) {
        assertTrue(new Parser(source).parse().isEmpty());
    }

    public Stream<Arguments> parse_shouldReturnNotValid_whenLinkInvalidCases() {
        return Stream.of(
                Arguments.of(
                        "https://stackoverflow.com/search?q=unsupported%20link"
                ),
                Arguments.of(
                        "https://github.com/"
                ),
                Arguments.of(
                        "https://mail.google.com/mail/u/0/#inbox"
                ),
                Arguments.of(
                        "https://github.com/issues"
                ),
                Arguments.of(
                        "https://stackoverflow.co/company/careers"
                ),
                Arguments.of(
                        "https://stackoverflow.co/company/work-here"
                )
        );
    }

}