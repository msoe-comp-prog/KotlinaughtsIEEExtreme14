/**
 * @author Nicholas Johnson
 * 
 * Score 4/6 (66%)
 */

#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>

#ifdef DEBUG
#define DBG(fmt, args...)    fprintf(stderr, fmt, ##args)
#else
#define DBG(fmt, args...)    // don't print anything in non-debug mode
#endif

typedef struct trie {
#ifdef DEBUG
    // If we're debugging, store the letter this trie holds as well for debugger helpfulness
    char letter;
#endif
    // Whether or not this node holds a word.
    uint8_t is_end_node;
    // The nodes below this node
    struct trie** children;
} trie;

// Creates a new root node for a trie.
trie* create_trie();
// Deletes a trie and sets the pointer to null.
void delete_trie(trie** trie);

// Adds a word to the trie. Returns 1 if added, 0 if existed.
uint8_t trie_add_word(trie* trie, const char* word);
// Removes a word from the trie. Returns 1 or 2 if removed, 0 if did not exist.
uint8_t trie_remove_word(trie* trie, const char* word);
// Returns 1 if the word exists in the trie, 0 otherwise.
uint8_t trie_has_word(const trie* trie, const char* word);
// Returns the number of distinct words in the trie.
size_t trie_number_words(const trie* trie);


trie* create_trie() {
    trie* trie = (struct trie*) calloc(1, sizeof(struct trie)); // calloc for zero-initializing fields

    if (!trie) {
        fprintf(stderr, "Could not allocate memory for a trie node!");
        exit(-1);
    }
    // Here, we zero-initialize pointers to children. This lets us dynamically allocate nodes if
    // need be, checking a child against NULL to see if it exists.
    trie->children = (struct trie**) calloc(36, sizeof(struct trie*));

    if (!trie->children) {
        fprintf(stderr, "Could not allocate memory for a trie node!");
        exit(-1);
    }

    return trie;
}

static void delete_trie_internal(trie* trie) {
    for (int i = 0; i < 36; ++i) {
        if (trie->children[i]) {
            delete_trie_internal(trie->children[i]);
        }
    }

    free(trie->children);
    free(trie);
}

// Takes a pointer to the trie pointer. This lets us delete the trie, then set the pointer to null
// so that a SEGFAULT appears sooner if a user deletes the trie and accidentally uses it again.
void delete_trie(trie** trie) {
    // Check that the trie has not already been deleted.
    if (*trie) {
        delete_trie_internal(*trie);
        *trie = NULL;
    }
}

// Exercise for the reader: Turn these recursive functions into looping functions
uint8_t trie_add_word(trie* trie, const char* word) {
    if (*word == '\0') { // if we've reached the end of the word
        // Get whether or not a word already exists here. We want to return 1 if we added and 0 if
        // it existed, but is_end_node will be the inverse of that.
        uint8_t res = !trie->is_end_node;
        trie->is_end_node = 1; // set to be an end node
        return res;
    }

    char idx = *word - 'A';
    if (idx < 0) {
        idx = *word - '0' + 26;
    } // map lowercase letters to 0-25
    if (!trie->children[idx]) { // if the child is null
        trie->children[idx] = create_trie(); // create a new child node
#ifdef DEBUG
        trie->children[idx]->letter = *word;
#endif
    }
    return trie_add_word(trie->children[idx], word + 1); // recursively go to next node with next character
}

// Here, we're checking if there are no children of this node. If there are no children, this node
// can be safely deleted.
static uint8_t trie_should_delete_node(const trie* trie) {
    for (uint_fast8_t i = 0; i < 36; i++) {
        if (trie->children[i]) {
            return 0;
        }
    }
    return 1;
}

// Attempts to remove a word from the trie. Returns 0 if the word was not in the trie.
uint8_t trie_remove_word(trie* trie, const char* word) {
    if (*word == '\0') {
        uint8_t res = trie->is_end_node;
        trie->is_end_node = 0;

        // Here, we signal the parent node that this node can be safely deleted. We do this by
        // returning 2. 2 is important since it is non-zero, which means that true/false if checks
        // will operate like returning 1. Additionally, we can check for that return value to
        // delete the child.

        // only check for deletion if this used to be a word node
        if (res && trie_should_delete_node(trie)) {
            return 2;
        }
        return res;
    }

    char idx = *word - 'A';
    if (idx < 0) {
        idx = *word - '0' + 26;
    }
    if (!trie->children[idx]) {
        return 0;
    }
    uint8_t res = trie_remove_word(trie->children[idx], word + 1);  // recursively go to next node with next character

    if (res == 2) { // child node signalled deletion
        delete_trie(&trie->children[idx]);

        if (trie_should_delete_node(trie)) { // check for our deletion
            return 2;
        }
        return 1;
    }
    return res;
}

uint8_t trie_has_word(const trie* trie, const char* word) {
    if (*word == '\0') { // If we're at the end of the word
        return trie->is_end_node; // Return whether or not we're an end node
    }
    char idx = *word - 'A';
    if (idx < 0) {
        idx = *word - '0' + 26;
    }
    if (!trie->children[idx]) { // if a letter in the word sequence does not exist
        return 0;
    }
    return trie_has_word(trie->children[idx], word + 1); // move to the next node with the next character
}

size_t trie_number_words(const trie* trie) {
    size_t res = trie->is_end_node;
    for (uint_fast8_t i = 0; i < 36; i++) {
        if (trie->children[i]) {
            res += trie_number_words(trie->children[i]);
        }
    }
    return res;
}


int main() {
    const char* ca = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    trie* trie = create_trie();
    int numStrings;
    scanf("%d", &numStrings);
    char** strs = malloc(numStrings * sizeof(char*));

    long total = 0;

    for (int i = 0; i < numStrings; ++i) {
        strs[i] = malloc(13 * sizeof(char));
        scanf("%4s-%4s-%4s", strs[i], strs[i] + 4, strs[i] + 8);
        strs[i][12] = 0;

        for (int j = 0; j < 12; ++j) {
            char og = strs[i][j];
            for (int k = 0; k < 36; ++k) {
                strs[i][j] = ca[k];
                if (trie_has_word(trie, strs[i])) {
                    ++total;
                }
            }
            strs[i][j] = og;
        }

        trie_add_word(trie, strs[i]);
    }
    printf("%ld\n", total);















//    printf("Number of words: %zu\n", trie_number_words(trie));
//    printf("Add 'test': %" PRIu8 "\n", trie_add_word(trie, "test"));
//    printf("Number of words: %zu\n", trie_number_words(trie));
//    printf("Add 'test': %" PRIu8 "\n", trie_add_word(trie, "test"));
//    printf("Number of words: %zu\n", trie_number_words(trie));
//    printf("Has 'testing': %" PRIu8 "\n", trie_has_word(trie, "testing"));
//    printf("Number of words: %zu\n", trie_number_words(trie));
//    printf("Has 'test': %" PRIu8 "\n", trie_has_word(trie, "test"));
//    printf("Add 'testing': %" PRIu8 "\n", trie_add_word(trie, "testing"));
//    printf("Has 'testing': %" PRIu8 "\n", trie_has_word(trie, "testing"));
//    printf("Number of words: %zu\n", trie_number_words(trie));
//    printf("Has 'test': %" PRIu8 "\n", trie_has_word(trie, "test"));
//    printf("Remove 'test': %" PRIu8 "\n", trie_remove_word(trie, "test"));
//    printf("Remove 'test': %" PRIu8 "\n", trie_remove_word(trie, "test"));
//    printf("Number of words: %zu\n", trie_number_words(trie));
//    printf("Has 'testing': %" PRIu8 "\n", trie_has_word(trie, "testing"));
//    printf("Has 'test': %" PRIu8 "\n", trie_has_word(trie, "test"));
//    printf("Remove 'testing': %" PRIu8 "\n", trie_remove_word(trie, "testing"));
//    delete_trie(&trie);
    return 0;
}

