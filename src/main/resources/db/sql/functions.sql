/*CREATE TYPE response_tr_dto AS
(
    transaction_id   UUID,
    external_id      TEXT,
    card_id          UUID,
    amount           BIGINT,
    after_balance    BIGINT,
    currency         TEXT,
    transaction_type TEXT,
    purpose          TEXT,
    exchange_rate    BIGINT
);

CREATE OR REPLACE FUNCTION get_transactions(
    p_card_id UUID DEFAULT NULL,
    p_transaction_name TEXT DEFAULT NULL,
    p_currency_name TEXT DEFAULT NULL,
    p_transaction_id UUID DEFAULT NULL,
    p_external_id TEXT DEFAULT NULL,
    p_limit INT DEFAULT 10,
    p_offset INT DEFAULT 0
)
    RETURNS SETOF response_tr_dto AS
$$
BEGIN
    RETURN QUERY
        SELECT t.id AS transaction_id,
               t.external_id,
               t.card_id,
               t.amount,
               t.after_balance,
               t.currency::TEXT,
               t.transaction_type::TEXT,
               t.purpose::TEXT,
               t.exchange_rate
        FROM transaction t
        WHERE (p_card_id IS NULL OR t.card_id = p_card_id)
          AND (p_transaction_name IS NULL OR t.transaction_type::TEXT = p_transaction_name)
          AND (p_currency_name IS NULL OR t.currency::TEXT = p_currency_name)
          AND (p_transaction_id IS NULL OR t.id = p_transaction_id)
          AND (p_external_id IS NULL OR t.external_id = p_external_id)
        LIMIT p_limit OFFSET p_offset;
END;
$$ LANGUAGE plpgsql;*/




