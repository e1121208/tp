package seedu.address.logic.parser.listingcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.listingcommands.AddListingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddListingCommand object
 */
public class AddListingCommandParser implements Parser<AddListingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddListingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_AREA, PREFIX_ADDRESS,
                        PREFIX_REGION, PREFIX_SELLER, PREFIX_BUYER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE, PREFIX_AREA, PREFIX_ADDRESS, PREFIX_REGION,
                PREFIX_SELLER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddListingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Area area = ParserUtil.parseArea(argMultimap.getValue(PREFIX_AREA).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Region region = ParserUtil.parseRegion(argMultimap.getValue(PREFIX_REGION).get());
        Name sellerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_SELLER).get());
        Set<Name> buyerNameList = ParserUtil.parseNames(argMultimap.getAllValues(PREFIX_BUYER));

        return new AddListingCommand(name, price, area, address, region, sellerName, buyerNameList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}